package org.tnmk.common.comparision;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ParentEntity {

    private String name;
    private ChildComparisionEntity childComparisionEntity;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass()) return false;
//
//        ParentEntity that = (ParentEntity) o;
//
//        return new EqualsBuilder()
//                .append(name, that.name)
//                .append(childComparisionEntity, that.childComparisionEntity)
//                .isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 37)
//                .append(name)
//                .append(childComparisionEntity)
//                .toHashCode();
//    }

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

    public ChildComparisionEntity getChildComparisionEntity() {
        return childComparisionEntity;
    }

    public void setChildComparisionEntity(ChildComparisionEntity childComparisionEntity) {
        this.childComparisionEntity = childComparisionEntity;
    }
}
