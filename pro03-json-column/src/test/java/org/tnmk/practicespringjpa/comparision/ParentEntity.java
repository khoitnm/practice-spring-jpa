package org.tnmk.practicespringjpa.comparision;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ParentEntity {

    private String name;
    private ChildEntity childEntity;

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildEntity getChildEntity() {
        return childEntity;
    }

    public void setChildEntity(ChildEntity childEntity) {
        this.childEntity = childEntity;
    }
}
