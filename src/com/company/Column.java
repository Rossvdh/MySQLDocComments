/*Class for column documentation of a stored proc/fun
 * Ross van der Heyde
 * 22 May 2018
 * */
package com.company;

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

    public Column() {

    }

    public Column(String name, String table, String alias) {
        this.name = name;
        this.table = table;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", table='" + table + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
