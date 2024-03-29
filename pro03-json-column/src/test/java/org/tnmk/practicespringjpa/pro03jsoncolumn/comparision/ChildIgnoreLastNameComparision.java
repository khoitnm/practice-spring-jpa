package org.tnmk.practicespringjpa.pro03jsoncolumn.comparision;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ChildIgnoreLastNameComparision {

    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChildIgnoreLastNameComparision that = (ChildIgnoreLastNameComparision) o;

        return new EqualsBuilder()
                .append(firstName, that.firstName)
//                .append(lastName, that.lastName)//ignore lastName
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstName)
//                .append(lastName)//ignore lastName
                .toHashCode();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
