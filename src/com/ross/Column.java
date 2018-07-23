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

    private String name = null;
    private String table = null;
    private String alias = null;

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
        String str = "";

        if (table != null) {
            str += table + ".";
        }

        str += name;

        if (alias != null) {
            str += " as " + alias;
        }

        return str;
    }

    /**
     * Creates a <code>{@link Column}</code> object from the given
     * <code>String</code>. The String is assumed to be of the one of the
     * forms:
     * "@col <TABLE>.<COLNAME> as <ALIAS>" "@col <TABLE>.<COLNAME>" "@col
     * <COLUMNNAME> as <ALIAS>" "@col <COLUMNNAME>" i.e. as it is in a MySQL
     * DocComment.
     *
     * @param line String to be parsed into a <code>{@link Column}</code>
     * @return a new {@link Column} object
     */
    public static Column parseColumn(String line) {
        Column col = new Column();

        String[] temp = line.split(" ");

        int len = temp.length;
        if (len == 2) {
            if (temp[1].contains(".")) {
                //"@col <TABLE>.<COLNAME>"

                String[] temp2 = temp[1].split(".");

                col.setTable(temp2[0]);
                col.setName(temp2[1]);
            } else {
                //"@col <COLUMNNAME>"
                col.setName(temp[1]);
            }

        } else if (len == 4) {
            /*System.out.println("-----");
            for (String s:temp ) {
                System.out.println(s);
            }
            System.out.println("-----");*/

            if (temp[1].contains(".")) {
                //"@col <TABLE>.<COLNAME> as <ALIAS>"
                String[] temp2 = temp[1].split("\\.");

                col.setTable(temp2[0]);
                col.setName(temp2[1]);
                col.setAlias(temp[3]);
            } else {
                //"@col <COLUMNNAME> as <ALIAS>"
                col.setName(temp[1]);
                col.setAlias(temp[3]);
            }
        } else {
            System.out.println("Error in Column.parseColumn: invalid temp " +
                    "array length.");
        }

        return col;
    }
}
