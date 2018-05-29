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
            Scanner inFile = new Scanner(new File(fileName));

            ArrayList<String> docCommentLines = new ArrayList<>();
            while (inFile.hasNext()) {
                String line = inFile.nextLine();

                if (line.startsWith("/**")) {
                    docCommentLines.add(line);
                } else if (line.endsWith("*/")) {
                    docComments.add(parseLines(docCommentLines));
                    docCommentLines.clear();
                }
            }

            //now output docComments to HTML file

        } catch (FileNotFoundException fnf) {
            System.out.println("The file " + fileName + " could not be found.");
            System.exit(0);
        }
    }

    /**
     * Parses the given Strings into a <code>DocComment</code>. Returns the
     * created
     * <code>{@link DocComment}</code>
     *
     * @param lines lines from file to parse into DocComment
     * @return the created <code>DocComment</code>
     */
    private static DocComment parseLines(ArrayList<String> lines) {

        return null;
    }
}
