/*Class for a documentation comment
 * Ross van der Heyde
 * 22 May 2018
 */
package com.ross;

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
}