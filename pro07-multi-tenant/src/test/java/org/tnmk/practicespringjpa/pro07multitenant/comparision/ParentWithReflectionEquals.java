package org.tnmk.practicespringjpa.pro07multitenant.comparision;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ParentWithReflectionEquals {

    private String name;
    private ChildIgnoreLastNameComparision childEntity;

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

    public ChildIgnoreLastNameComparision getChildEntity() {
        return childEntity;
    }

    public void setChildEntity(ChildIgnoreLastNameComparision childEntity) {
        this.childEntity = childEntity;
    }
}
