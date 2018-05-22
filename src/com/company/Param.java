/*Class for parameter documentation of a store proc/fun
 * Ross van der Heyde
 * 22 May 2018
 * */
package com.company;

/**
 * Param is a class representing a parameter of a stored function or procedure.
 * It contains the type, formal argument name and description
 */
public class Param {
    private String type;
    private String name;
    private String description;

    /**
     * Constructs an empty Param
     */
    public Param() {

    }

    /**
     * Constructs a Param with the given values
     *
     * @param type        String with the type of the parameter
     * @param name        String with name of the parameter
     * @param description String with the description of the parameter
     */
    public Param(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Param{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
