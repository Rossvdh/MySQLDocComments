/*Class for return documentation of a stored proc/fun
 * Ross van der Heyde
 * 22 May 2018
 * */
package com.ross;

/**
 * This class represents a documentation entry for what is returned by a stored
 * function. It has a type (the return type) and the description of the return
 * value
 */
public class Return {
    private String type;
    private String description;

    /**
     * Constructs an empty <code>Return</code> object
     */
    public Return() {

    }

    /**
     * Constructs a <code>Return</code> with the given type and description
     *
     * @param type        The type (int, VARCHAR, etc)
     * @param description Brief description of the return variable
     */
    public Return(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Returns the type of <code>this Return</code>
     *
     * @return type of the return value
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of <code>this Return</code>
     *
     * @param type type of this <code>Return</code> value
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the description of this <code>{@link Return}</code>
     *
     * @return brief description of <code>this</code>
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of this <code>{@link Return}</code>
     *
     * @param description a brief description of this <code>{@link
     *                    Return}</code>
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a <code>String</code> representation of this <code>{@link
     * Return}</code>
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "Return{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Creates a <code>Return</code> object from the given
     * <code>String</code>. The String is assumed to be of the form "@return
     * <TYPE> <DESCRIPTION>", i.e. as it is in a MySQL DocComment.
     *
     * @param line String to be parsed into a <code>{@link Return}</code>
     * @return a new {@link Return} object
     */
    public static Return parseReturn(String line) {
        Return ret = new Return();

        String[] temp = line.split(" ", 3);

        ret.setType(temp[1]);
        ret.setDescription(temp[2]);

        return ret;
    }
}
