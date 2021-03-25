package org.tnmk.practicespringjpa.pro03jsoncolumn.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

public class ChildWithoutMapComparisionEntity {

    private String name;

    private String description;

    private Map<String, String> characteristics;

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof ChildWithoutMapComparisionEntity)) {
            return false;
        }

        ChildWithoutMapComparisionEntity that = (ChildWithoutMapComparisionEntity) obj;
        boolean isEquals = new EqualsBuilder()
                .append(name, that.name)
                .append(description, that.description)
                //Note: when we compare Map.equals(), it also compares entries inside the Maps (AbstractList also works in a similar way)
                //.append(characteristics, that.characteristics) //We intentionally ignore this field
                .isEquals();
        return isEquals;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(description)
                //.append(characteristics) //We intentionally ignore this field
                .build();
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

    public Map<String, String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Map<String, String> characteristics) {
        this.characteristics = characteristics;
    }
}
