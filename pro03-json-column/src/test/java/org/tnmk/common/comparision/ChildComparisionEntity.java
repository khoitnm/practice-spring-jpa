package org.tnmk.common.comparision;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ChildComparisionEntity {

    private String fieldA;
    private String fieldB;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChildComparisionEntity that = (ChildComparisionEntity) o;

        return new EqualsBuilder()
                .append(fieldA, that.fieldA)
//                .append(fieldB, that.fieldB)//ignore fieldB
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fieldA)
//                .append(fieldB)//ignore fieldB
                .toHashCode();
    }

    public String getFieldA() {
        return fieldA;
    }

    public void setFieldA(String fieldA) {
        this.fieldA = fieldA;
    }

    public String getFieldB() {
        return fieldB;
    }

    public void setFieldB(String fieldB) {
        this.fieldB = fieldB;
    }
}
