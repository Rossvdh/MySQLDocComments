/*A program to extract javadoc style comments from a .sql file of stored functions
 and procedures adn output an html file.
*
* Ross van der Heyde
* 21 May 2018*/

package com.ross;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /**
     * @param args name of the .sql file to parse
     */
    public static void main(String[] args) {
        String inputFileName = args[0];
        System.out.println("file to parse: " + inputFileName);

        String outputFileName = args[1];
        System.out.println("Output file name: " + outputFileName);

        ArrayList<DocComment> docComments = new ArrayList<>();

        try {
            System.out.print("Opening file . . .");
            Scanner inFile = new Scanner(new File(inputFileName));
            System.out.println("Done");

            System.out.println("Parsing comments . . .");
            int count = 0;
            boolean addToComment = false;
            ArrayList<String> docCommentLines = new ArrayList<>();

            while (inFile.hasNext()) {
                String line = inFile.nextLine();

                if (line.trim().startsWith("/**")) {
                    //start of new doc comment
                    docCommentLines = new ArrayList<>();
                    addToComment = true;
                } else if (line.contains("*/")) {
                    //end of doc comment
                    addToComment = false;

                    //consume "DELIMITER ;;" line
                    inFile.nextLine();
                    //consume declaration line
                    String nameLine = inFile.nextLine();

                    DocComment comment = DocComment.parseLines(docCommentLines,
                            nameLine);
                    docComments.add(comment);

                    count++;
                    System.out.println("\t" + count + " " + comment.getName());
                }

                //add line to current comment
                if (addToComment) {
                    docCommentLines.add(line);
                }
            }
            System.out.println("Done");

            //now output docComments to HTML file
            outputToHTML(docComments, outputFileName);

        } catch (FileNotFoundException fnf) {
            System.out.println("The file " + inputFileName + " could not be found.");
            System.exit(0);
        }
    }


    /**
     * Outputs the docComments to an HTML file.
     *
     * @param docComments ArrayList of <code>{@link DocComment}</code>s to
     *                    output
     * @param outputFileName name of the html file to write output to.
     */
    private static void outputToHTML(ArrayList<DocComment> docComments,
                                     String outputFileName) {
        System.out.print("Outputting to HTML file ...\n");

        //open file
        FileWriter outFile;
        try {
            //open template file
            System.out.print("Reading template file. . .");
            Scanner templateIn = new Scanner(new File("template.html"));
            ArrayList<String> templateLines = new ArrayList<>();

            while (templateIn.hasNext()) {
                templateLines.add(templateIn.nextLine());
            }
            templateIn.close();
            System.out.println("Done");

            //write out to output file until <!--##
            System.out.println("Writing to output file. . .");
            int startAt = 0;
            outFile = new FileWriter(new File(outputFileName));

            System.out.print("\tfirst part of template. . .");
            for (String line : templateLines) {
                startAt++;
                if (!line.trim().startsWith("<!--#")) {
                    outFile.write(line + "\n");
                } else {
                    break;
                }
            }
            System.out.println("Done");

            //write brief output
            System.out.print("\tWriting brief descriptions . . .");
            for (DocComment comment : docComments) {
                comment.outputBrief(outFile);
            }
            System.out.println("Done");

            //write from template
            System.out.print("\tWriting second part of template. . .");
            for (int i = startAt; i < templateLines.size(); i++) {
                startAt++;
                String line = templateLines.get(i);
                if (!line.trim().startsWith("<!--#")) {
                    outFile.write(line + "\n");
//                    startAt++;
                } else {
                    break;
                }
            }
            System.out.println("Done");

            //write complete output
            System.out.print("\tWriting complete descriptions. . .");
            int divClass = 0;
            for (DocComment comment : docComments) {
                comment.outputComplete(outFile, divClass);
                divClass++;
            }
            System.out.println("Done");

            //write last part of template
            System.out.print("\tLast part of template. . .");
            for (int i = startAt; i < templateLines.size(); i++) {
                outFile.write(templateLines.get(i) + "\n");
            }
            System.out.println("Done");

            outFile.close();
            System.out.println("Done");

        } catch (FileNotFoundException fnf) {
            System.out.println("Output file not found");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
