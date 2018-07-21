/*A program to extract javadoc style comments from a .sql file of stored functions
 and procedures adn output an html file.
*
* Ross van der Heyde
* 21 May 2018*/

package com.ross;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     * @param args name of the .sql file to parse
     */
    public static void main(String[] args) {
        String fileName = args[0];
        System.out.println("file to parse: " + fileName);

        ArrayList<DocComment> docComments = new ArrayList<>();

        try {
            System.out.print("Opening file . . .");
            Scanner inFile = new Scanner(new File(fileName));
            System.out.println("Done");

            System.out.print("Parsing comments . . .");
            int count = 0;
            boolean addToComment = false;
            ArrayList<String> docCommentLines = new ArrayList<>();

            while (inFile.hasNext()) {
                String line = inFile.nextLine();

                if (line.startsWith("/**")) {
                    //start of new doc comment
                    docCommentLines = new ArrayList<>();
                    addToComment = true;
                } else if (line.endsWith("*/")) {
                    //end of doc comment
                    addToComment = false;

                    docComments.add(parseLines(docCommentLines, inFile));

                    count++;
                    System.out.print(count + " ");
                }

                //add line to current comment
                if (addToComment) {
                    docCommentLines.add(line);
                }
            }

            //now output docComments to HTML file
            System.out.print("Done\nOutputting to HTML file ...\n");
            for (DocComment comment : docComments) {
                System.out.println(comment.toString() + "\n");
            }
            System.out.println("Done");
        } catch (FileNotFoundException fnf) {
            System.out.println("The file " + fileName + " could not be found.");
            System.exit(0);
        }
    }

    /**
     * Parses the given Strings into a <code>DocComment</code>. Returns the
     * created <code>{@link DocComment}</code>
     *
     * @param lines lines from file to parse into DocComment
     * @return the created <code>DocComment</code>
     */
    private static DocComment parseLines(ArrayList<String> lines, Scanner
            inFile) {
        DocComment comment = new DocComment();

        inFile.nextLine();
        String nameLine = inFile.nextLine();
        String name = "";
        if (nameLine.contains("FUNCTION")) {
            int nameStart = nameLine.indexOf("FUNCTION") + "FUNCTION ".length();
            int nameEnd = nameLine.indexOf("(");

            name = nameLine.substring(nameStart, nameEnd);

        } else {
            //PROCEDURE
            int nameStart = nameLine.indexOf("PROCEDURE") + "PROCEDURE ".length();
            int nameEnd = nameLine.indexOf("(");

            name = nameLine.substring(nameStart, nameEnd);
        }

        name = name.replace("`", "");
        name = name.replace("'", "");
        name = name.replace("\"", "");
        comment.setName(name);

        String descrip = "";
        for (String line : lines) {
            String temp = line.trim();

            if (temp.startsWith("@return")) {
                //@return <TYPE> <DESCRIPTION>
                Return ret = Return.parseReturn(temp);

                comment.setReturn(ret);
            } else if (temp.startsWith("@param")) {
                //@param <TYPE> <NAME> <DESCRIPTION>
                Param param = Param.parseParam(line);

                comment.addParam(param);
            } else if (temp.startsWith("@col")) {
                //@col <TABLE>.<COLNAME> as <ALIAS>
                //@col <TABLE>.<COLNAME>
                //@col <COLUMNNAME> as <ALIAS>
                //@col <COLUMNNAME>
                Column col = Column.parseColumn(line);

                comment.addColumn(col);
            } else {
                //assume part of description
                descrip += temp;
            }
        }

        comment.setDescription(descrip);
//        System.out.println("# " + comment);
        return comment;
    }


}
