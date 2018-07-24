package com.ross;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class representing a stored procdeure (Stored PROCedure) or stored
 * function.
 */

public class Sproc {
    private String name = null;
    private boolean isFunction;
    private ArrayList<Param> params;
    private ArrayList<Column> cols;
    private Return ret;
    private DocComment docComment = null;

    /**
     * Default Constructor
     */
    public Sproc() {
        params = new ArrayList<>();
        cols = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     *
     * @param name the name of this Sproc
     */
    public Sproc(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this <code>Sproc</code>
     *
     * @return the name of this <code>Sproc</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this <code>{@link Sproc}</code>
     *
     * @param name name of this <code>{@link Sproc}</code>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns <code>true</code> if this <code>{@link Sproc}</code> is a
     * function, <code>false</code> if a procedure
     *
     * @return true if a function
     */
    public boolean isFunction() {
        return isFunction;
    }

    /**
     * Set to <code>true</code> if this <code>{@link Sproc}</code> is a
     * function, <code>false</code> if a procedure
     *
     * @param function true if a function
     */
    public void setFunction(boolean function) {
        isFunction = function;
    }

    /**
     * Returns an <code>ArrayList</code> of the <code>{@link Param}</code>s of
     * this
     * <code>Sproc</code>
     *
     * @return <code>ArrayList</code> of the <code>{@link Param}</code>s
     */
    public ArrayList<Param> getParams() {
        return params;
    }

    /**
     * Set the <code>ArrayList\<Params\></code> of this <code>{@link
     * Sproc}</code>
     *
     * @param params <code>ArrayList\<Params\></code>
     */
    public void setParams(ArrayList<Param> params) {
        this.params = params;
    }

    /**
     * Adds a <code>{@link Param}</code> to this <code>{@link Sproc}</code>'d
     * list of parameters
     *
     * @param p <code>{@link Param}</code> to add
     */
    public void addParam(Param p) {
        params.add(p);
    }

    /**
     * Returns an <code>ArrayList</code> of the <code>{@link Column}</code>s of
     * this
     * <code>Sproc</code>
     *
     * @return <code>ArrayList</code> of the <code>{@link Column}</code>s
     */
    public ArrayList<Column> getCols() {
        return cols;
    }

    /**
     * Set the <code>ArrayList\<Params\></code> of this <code>{@link
     * Sproc}</code>
     *
     * @param cols <code>ArrayList\<Params\></code>
     */
    public void setCols(ArrayList<Column> cols) {
        this.cols = cols;
    }

    /**
     * Adds a <code>{@link Column}</code> to this <code>{@link Sproc}</code>'d
     * list of columns returned
     *
     * @param c <code>{@link Column}</code> to add
     */
    public void addColumn(Column c) {
        cols.add(c);
    }

    /**
     * Returns the <code>Ret</code> of this <code>{@link Sproc}</code> (which is
     * a function)
     *
     * @return <code>Ret</code> of this <code>{@link Sproc}</code>
     */
    public Return getRet() {
        return ret;
    }

    /**
     * Set the <code>Ret</code> value of this <code>{@link Sproc}</code>.
     *
     * @param ret <code>Ret</code> value of this <code>{@link Sproc}</code>
     */
    public void setRet(Return ret) {
        this.ret = ret;
    }

    /**
     * Returns the <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>.
     *
     * @return <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>.
     */
    public DocComment getDocComment() {
        return docComment;
    }

    /**
     * Set the <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>.
     *
     * @return <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>.
     */
    public void setDocComment(DocComment docComment) {
        this.docComment = docComment;
    }

    /**
     * Returns true if this <code>{@link Sproc}</code> has a <code>{@link
     * DocComment}</code>
     *
     * @return true if a <code>{@link DocComment}</code> is present
     */
    public boolean hasDocComment() {
        return docComment == null;
    }

    /**
     * Returns the <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>
     *
     * @return <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>
     */
    public DocComment toDocComment() throws Exception {

        throw new OperationNotSupportedException("Sproc.toDocComment has not " +
                "been implemented yet");
    }

    /**
     * Parses the given lines of SQL and creates a <code>{@link Sproc}</code>
     *
     * @param lines <code>{@link ArrayList}</code>\<String\> of lines
     * @return <code>{@link Sproc}</code> built from the given lines
     */
    public static Sproc linesToSproc(ArrayList<String> lines) {
        System.out.println("Sproc.linesToSproc");
        Sproc sproc = new Sproc();

        //get name, params, and return from first line
        Scanner s = new Scanner(lines.get(0));
        boolean ps = false;
        while (s.hasNext()) {
            String word = s.next();

            if (word.equals("PROCEDURE") || word.equals("FUNCTION")) {
                //next is name(param1

                String[] temp = s.next().replace("`", "").split("\\(");

                sproc.setName(temp[0]);

                //first param
                Param p = new Param();
                p.setName(temp[1]);
                p.setType(s.next().replace(",", ""));
                sproc.addParam(p);
                ps = true;
                continue;
            } else if (word.equals("RETURNS")) {
                ps = false;
                //next is return type
                word = s.next();
                sproc.setRet(new Return(word, ""));
                continue;
            }

            //second and further params
            if (ps) {
                Param p = new Param();
                p.setName(word);
                p.setType(s.next().replace("(", ""));
                sproc.addParam(p);
            }
        }

        //now only cols (if any)
        if (!sproc.isFunction()) {
            //build query text to extract column names from
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < lines.size(); i++) {
                sb.append(lines.get(i));
            }
            Scanner queryScan = new Scanner(sb.toString());

            if (queryScan.next().equalsIgnoreCase("SELECT")) {
                String word = "";
                ArrayList<Column> cols = new ArrayList<>();

                while (queryScan.hasNext() && !((word = queryScan.next())
                        .equalsIgnoreCase("FROM"))) {
                    Column col = new Column();

                    word = word.replace(",", "");

                    if (word.contains(".")) {
                        String[] temp = word.split("\\.");

                        col.setTable(temp[0]);
                        col.setName(temp[1]);
                        cols.add(col);
                    } else if (word.equalsIgnoreCase("AS")) {
                        cols.get(cols.size() - 1).setAlias(queryScan.next()
                                .replace(",", ""));
                    } else {
                        col.setName(word);
                        cols.add(col);
                    }
                }

                sproc.setCols(cols);
            }
        }

        System.out.println("sproc.getName() = " + sproc.getName());
        System.out.println("sproc.getCols() = " + sproc.getCols());
        System.out.println("end Sproc.linesToSproc\n");
        return sproc;
    }
}
