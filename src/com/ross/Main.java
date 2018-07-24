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
        System.out.println("SQL stored procedure and function documentation " +
                "generator.");
        //parse args
        String sqlFileName = null;
        String commentFileName = "docCommentOutput.sql";
        String htmlFileName = "docOutput.html";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-sql":
                case "-s": {
                    //next is the .sql fileName
                    i++;
                    sqlFileName = args[i];
                    System.out.println("SQL file name: " + sqlFileName);
                    break;
                }
                case "-comments":
                case "-c": {
                    //next is comments fileName
                    i++;
                    commentFileName = args[i];
                    System.out.println("Doc comment output file name: " +
                            commentFileName);
                    break;
                }
                case "-html":
                case "-h": {
                    //next is html fileName
                    i++;
                    htmlFileName = args[i];
                    System.out.println("HTML Documentation " +
                            "output file name: " + htmlFileName);
                    break;
                }
                default: {
                    System.err.println("Invalid option: " + args[i]);
                    System.exit(-1);
                }
            }
        }

        if (sqlFileName == null) {
            /*main -comments fileWithComments.sql -html file.html
            takes fileWithComments (which MUST have docComments) and output file.html with HTML docs*/

            ArrayList<DocComment> docComments = linesToDocComments(commentFileName);

            //generate HTML
            //write HTML to htmlFileName
            outputToHTML(docComments, htmlFileName);

        } else if (commentFileName.equals("docCommentOutput.sql")) {
            /*main -sql file.sql -html file.html
            takes file.sql (which has sproc code (docComments or not)) and output file.html with HTML docs.
            (generates docComments for those who don't have)*/

            //read from sqlFileName
            //parse SQL into Sprocs
            ArrayList<Sproc> sprocs = linesToSprocs(sqlFileName);

            //generate DocComments (for those Sprocs where it is necessary)
            ArrayList<DocComment> docComments = sprocsToDocComments(sprocs);

            //write HTML to htmlFileName
            outputToHTML(docComments, htmlFileName);

            System.err.println("Not supported yet");
        } else if (htmlFileName.equals("docOutput.html")) {
            /*main -sql file.sql -comments fileWithComments.sql
            takes file.sql (which has sproc code (docComments or not)) and output
             fileWithComments.sql with docComments for all sprocs*/
            System.out.println("yyyyes");
            //read from sqlFileName
            //parse SQL into Sprocs
            ArrayList<Sproc> sprocs = linesToSprocs(sqlFileName);

            //generate DocComments (for those Sprocs where it is necessary)
            ArrayList<DocComment> docComments = sprocsToDocComments(sprocs);

            //write DocComments and SQL code to commentFileName
            outputToSQL(docComments, sqlFileName, commentFileName);
            System.err.println("Not supported yet");
        } else {
            /*main -sql file.sql -comments fileWithComments.sql -html file.html
            takes file.sql (which has sproc code (docComments or not)) and output fileWithComments.sql with docComments for all sprocs,
            and outputs file.html with HTML docs*/

            //read sqlFileName
            //parse SQL into Sprocs
            ArrayList<Sproc> sprocs = linesToSprocs(sqlFileName);

            //generate DocComments (for those Sprocs where it is necessary)
            ArrayList<DocComment> docComments = sprocsToDocComments(sprocs);

            //write DocComments to commentFileName
            outputToSQL(docComments, sqlFileName, commentFileName);

            //write HTML to htmlFileName
            outputToHTML(docComments, htmlFileName);
            System.err.println("Not supported yet");
        }
    }

    /**
     * Reads from the .sql file with comments and parses the text into
     * <code>{@link DocComment}</code>s
     */
    private static ArrayList<DocComment> linesToDocComments(String
                                                                    commentFileName) {
        ArrayList<DocComment> docComments = new ArrayList<>();

        //read from commentFileName
        Scanner inFile = null;
        try {
            System.out.print("Opening file . . .");
            inFile = new Scanner(new File(commentFileName));
            System.out.println("Done");
        } catch (FileNotFoundException fnf) {
            System.out.println("The file '" + commentFileName + "' was not found");
            System.exit(-1);
        }

        //parse DocComments
        //(no docComments are generated from SQL)
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

        return docComments;
    }

    /**
     * Outputs the docComments to an HTML file.
     *
     * @param docComments    ArrayList of <code>{@link DocComment}</code>s to
     *                       output
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

    /**
     * Reads the .sql file and generates an <code>{@link ArrayList}</code> of
     * <code>{@link Sproc}</code> objects
     *
     * @param sqlFileName file with stored procedure/function code
     * @return ArrayList<Sproc>
     */
    private static ArrayList<Sproc> linesToSprocs(String sqlFileName) {
        System.out.println("Parsing stored function and procedure code into " +
                "Sprocs. . .");
        ArrayList<Sproc> sprocs = new ArrayList<>();

        Scanner inFile = null;
        try {
            //open file
            System.out.print("\tOpening '" + sqlFileName + "'. . .");
            inFile = new Scanner(new File(sqlFileName));
        } catch (FileNotFoundException fnf) {
            System.out.println("The file '" + sqlFileName + "' was not found");
            System.exit(-1);
        }
        System.out.println("Done");

        ArrayList<String> sprocLines = null;
        boolean addToLines = false;
        int count = 0;

        while (inFile.hasNext()) {
            String line = inFile.nextLine();

            if (line.startsWith("CREATE")) {
                //start of procedure declaration
                sprocLines = new ArrayList<>();
                addToLines = true;
            } else if (line.endsWith("END ;;")) {
                addToLines = false;
                count++;

                Sproc sproc = Sproc.linesToSproc(sprocLines);
                sprocs.add(sproc);

                //System.out.println("\t"+count +" " + sproc.getName());
            }

            if (addToLines) {
                sprocLines.add(line.trim());
            }
        }

        System.out.print("\tClosing '" + sqlFileName + "'. . .");
        inFile.close();
        System.out.println("Done");
        System.out.println("Done");
        return sprocs;
    }

    /**
     * Generates an <code>{@link ArrayList}</code> of
     * <code>{@link DocComment}</code> objects from the given <code>{@link
     * ArrayList}</code> of
     * * <code>{@link Sproc}</code>s.
     * <p>
     * More accurately, it generates DocComments for those Sprocs that do not
     * have DocComments, and then creates an ArrayList of DocComments from the
     * ArrayList of Sprocs/
     *
     * @param sprocs ArrayList<Sproc>
     * @return ArrayList<DocComments>
     */
    private static ArrayList<DocComment> sprocsToDocComments
    (ArrayList<Sproc> sprocs) {
        System.err.println("Main.sprocsToDocComments has not " +
                "been implemented yet");
        return null;
    }

    /**
     * Writes the DocComments and SQL code for the sprocs (from sqlFileName) to
     * commentFileName
     *
     * @param docComments     docComments to write to file
     * @param sqlFileName     file to read SQL code from
     * @param commentFileName file to write DocComments and SQL sproc code to.
     */
    private static void outputToSQL(ArrayList<DocComment> docComments,
                                    String sqlFileName,
                                    String commentFileName) {
        //read sqlFileName into ArrayList first, because we might be writing
        // to sqlFileName as well.
        System.err.println("Main.outputToSQL has not " +
                "been implemented yet");
    }
}
