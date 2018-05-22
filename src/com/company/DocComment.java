/*Class for a documentation comment
 * Ross van der Heyde
 * 22 May 2018
 */
package com.company;

import java.util.ArrayList;

/**
 * This class represents a documentation comment found in a .sql file. The
 * comment provides information regarding a stored procedure or function. It is
 * very similar to a JavaDoc comment.
 */
public class DocComment {
    private String description;
    private ArrayList<Param> param;
    private ArrayList<Column> cols;
    private Return ret;

    /**
     * Default DocComment constructor
     */
    public DocComment() {

    }


}