package com.ross;

import java.util.ArrayList;

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

    /**
     * Default Constructor
     */
    public Sproc() {

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
     * <code>{@link Sproc}</code>
     *
     * @return <code>{@link DocComment}</code> that documents this
     * <code>{@link Sproc}</code>
     */
    public DocComment toDocComment() {
        return null;
    }

    /**
     * Parses the given lines of SQL and creates a <code>{@link Sproc}</code>
     *
     * @param lines <code>{@link ArrayList}</code>\<String\> of lines
     */
    public static DocComment parseToSproc(ArrayList<String> lines) {
        return null;
    }
}
