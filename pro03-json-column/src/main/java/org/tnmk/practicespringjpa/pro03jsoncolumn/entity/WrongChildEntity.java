package org.tnmk.practicespringjpa.pro03jsoncolumn.entity;

/**
 * The only different with {@link ChildEntity} is that this class doesn't override equals() and hashCode())
 */
public class WrongChildEntity {

    private String name;

    private String description;

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
}
