/*Class for a documentation comment
 * Ross van der Heyde
 * 22 May 2018
 */
package com.ross;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents a documentation comment found in a .sql file. The
 * comment provides information regarding a stored procedure or function. It is
 * very similar to a JavaDoc comment.
 */
public class DocComment {
    private String name;
    private String description;
    private ArrayList<Param> params;
    private ArrayList<Column> cols;
    private Return ret = null;

    /**
     * Default DocComment constructor
     */
    public DocComment() {
        params = new ArrayList<>();
        cols = new ArrayList<>();
    }

    /**
     * Add a <code>Param</code> to the <code>ArrayList</code> of
     * <code>Param</code>s in this <code>{@link DocComment}</code>
     *
     * @param p <code>Param</code> to add.
     */
    public void addParam(Param p) {
        params.add(p);
    }

    /**
     * Add a <code>Column</code> to the <code>ArrayList</code> of
     * <code>Columns</code>s in this <code>{@link DocComment}</code>
     *
     * @param c <code>Column</code> to add.
     */
    public void addColumn(Column c) {
        cols.add(c);
    }

    /**
     * Returns the description of <code>this</code> {@link DocComment}
     *
     * @return String with description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set he description of <code>this</code> {@link DocComment}
     *
     * @param description String with description of the function/procedure
     */
    public void setDescription(String description) {
        this.description = description.replace("/**", "");
    }

    /**
     * Returns an <code>{@link ArrayList}<Param></code> of the the
     * <code>Param</code>s of the function/procedure.
     *
     * @return ArrayList of parameters to this function/procedure
     */
    public ArrayList<Param> getParams() {
        return params;
    }

    /**
     * Set the <code>{@link ArrayList}<Param></code> of <code>Param</code>s to
     * this stored procedure.
     *
     * @param params ArrayList of Params
     */
    public void setParams(ArrayList<Param> params) {
        this.params = params;
    }

    /**
     * Return an <code>{@link ArrayList}<Column></code> of the columns in the
     * <code>{@link java.sql.ResultSet}</code> returned by this stored
     * procedure.
     *
     * @return ArrayLIst of Columns
     */
    public ArrayList<Column> getCols() {
        return cols;
    }

    /**
     * Set the <code>{@link ArrayList}<Column></code> of the columns in the *
     * <code>{@link java.sql.ResultSet}</code> returned by this stored *
     * procedure.
     *
     * @param cols ArrayList of Columns returned by this stored procedure.
     */
    public void setCols(ArrayList<Column> cols) {
        this.cols = cols;
    }

    /**
     * Return the <code>{@link Return}</code> returned by this stored function
     *
     * @return Return object specified by the stored function
     */
    public Return getReturn() {
        return ret;
    }

    /**
     * Set the <code>{@link Return}</code> returned by this stored function
     *
     * @param ret <code>Return</code> object specified by the stored function
     */
    public void setReturn(Return ret) {
        this.ret = ret;
    }

    /**
     * Returns the name of the function or procedure that this
     * <code>{@link DocComment}</code> documents.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the function or procedure that this * <code>{@link
     * DocComment}</code> documents.
     *
     * @param name Name of the function or procedure
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a <code>String</code> representatio of this <code>{@link
     * DocComment}</code>
     *
     * @retun String representing this {@link DocComment}
     */
    public String toString() {
        return name + ": " + description + ".\n Params: " + params.toString() +
                "\n" + (ret != null ? ret.toString() :
                "Columns: " + cols.toString());
    }

    /**
     * Writes the brief version of this <code>{@link DocComment}</code> to the
     * given file output stream. The brief version is an HTML table row element
     * with 3 table data entries: return, name, parameters
     *
     * @param outFile FileWrite which writes output
     */
    public void outputBrief(FileWriter outFile) {
        /*
        <tr>
            <th>Return</th>
            <th>Name</th>
            <th>Parameters</th>
         </tr>*/
        try {
            outFile.write("<tr>\n");
            outFile.write("\t<td>");
            if (ret != null) {
                outFile.write(ret.getType() + "</td>\n");
            } else {
                outFile.write("<i>columns</i></td>\n");
            }

            outFile.write("<td><b>" + name + "</b></td>");

            outFile.write("<td>");
            for (int i = 0; i < params.size(); i++) {
                if (i != params.size() - 1) {
                    outFile.write(params.get(i).getType() + " " + params.get(i).getName() + ", ");
                } else {
                    //last one
                    outFile.write(params.get(i).getType() + " " + params.get(i).getName());
                }
            }
            outFile.write("</td>\n");

            outFile.write("</tr>\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Writes the complete version of this <code>{@link DocComment}</code> to
     * the given file output stream.
     *
     * @param outFile FileWrite which writes output
     */
    public void outputComplete(FileWriter outFile, int divClass) {
        try {
            outFile.write("<div class=\"test" + (divClass % 2) + "\">");
            if (isFunction()) {
                outFile.write("<!-- Function -->");
            } else {
                outFile.write("<!-- Procedure-->");
            }

            outFile.write("<h6>" + name + "</h6>");
            outFile.write("<p>" + description + "</p>");

            outFile.write("<p>Parameters</p>");
            outFile.write("<table class=\"tableClass\">");


            //write out parameters
            for (Param p : params) {
                outFile.write("<tr>");
                outFile.write("<td>" + p.getName() + "</td>");
                outFile.write("<td><b>" + p.getType() + "</b></td>");
                outFile.write("<td>" + p.getDescription() + "</td>");
                outFile.write("</tr>");
            }
            outFile.write("</table>");

            //return or columns
            if (isFunction()) {
                outFile.write("<p>Returns: " + ret.getType() + ", " + ret
                        .getDescription() + "</p>");

            } else {
                //is procedure, so columns
                outFile.write("<p>Column set</p>");

                outFile.write("<table class=\"columns\">");
                outFile.write("<tr>");

                for (Column c : cols) {
                    outFile.write("<td>" + c.getName() + "</td>");
                }
                        /*<td>Name as Alias</td>
                        <td>Table.Name</td>
                        <td>Table.Name as Alias</td>*/
                outFile.write("</tr>");
                outFile.write("</table>");
            }
            outFile.write("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if the stored proc documented by this <code>{@link
     * DocComment}</code> is a function
     *
     * @return true if it is a function
     */
    public boolean isFunction() {
        return ret != null;
    }
}