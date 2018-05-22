/*Class for return documentation of a stored proc/fun
 * Ross van der Heyde
 * 22 May 2018
 * */
package com.company;

/**
 * This class represents a documentation entry for what is returned by a stored
 * function. It has a type (the return type) and the description of the return
 * value
 */
public class Return {
    private String type;
    private String description;

    public Return() {

    }

    public Return(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Return{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
