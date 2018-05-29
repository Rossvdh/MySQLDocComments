/*Class for column documentation of a stored proc/fun
 * Ross van der Heyde
 * 22 May 2018
 * */
package com.ross;

/**
 * This class represents a column that is returned by the query. It always has a
 * name, and sometimes a table name and alias. The name is the name of the
 * column as it appears in the query The table is the table that the column is
 * in. The alias is the name of the column as it appears in the query output.
 * <p>
 * Example: in "SELECT AllBooks.id as bookID, title, Authors.id as authorID,
 * name, role FROM...", "AllBooks.id as bookID" would be extracted as the first
 * column, where "AllBooks" is the table, "id" is the name, and "bookID" is the
 * alias.
 */
public class Column {

    private String name;
    private String table;
    private String alias;

    /**
     * Construct an empty <code>Column</code>
     */
    public Column() {

    }

    /**
     * Construct a <code>{@link Column}</code> with the given parameters
     *
     * @param name  Column name
     * @param table Table the Columns is in
     * @param alias Display name column
     */
    public Column(String name, String table, String alias) {
        this.name = name;
        this.table = table;
        this.alias = alias;
    }

    /**
     * Return this <code>Column</code>'s name
     *
     * @return column name
     */
    public String getName() {
        return name;
    }

    /**
     * Set this <code>Column</code>'s name
     *
     * @param name column name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the name of the table that this <code>{@link Column}</code> is
     * part of.
     *
     * @return table this Column belongs to
     */
    public String getTable() {
        return table;
    }

    /**
     * Set the name of the table that this <code>{@link Column}</code> is part
     * of.
     *
     * @param table table this Column belongs to
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Returns the alias (name of the column as in the returned
     * <code>ResultSet</code>) of this <code>Column</code>
     *
     * @return alias of this <code>Column</code>
     */
    public String getAlias() {
        return alias;
    }

    /*** Set the alias (name of the column as in the returned
     * <code>ResultSet</code>) of this <code>Column</code>
     * @param alias alias of this <code>Column</code>
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Returns a <code>String</code> representation of this
     * <code>{@link Column}</code>
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", table='" + table + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
