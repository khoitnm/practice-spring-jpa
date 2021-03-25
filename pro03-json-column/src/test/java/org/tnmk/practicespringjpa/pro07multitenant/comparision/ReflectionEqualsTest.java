package org.tnmk.practicespringjpa.pro07multitenant.comparision;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionEqualsTest {

    @Test
    public void test_reflectionEquals_will_use_our_equals_implementation_instead_of_blindly_compare_all_fields() {
        ChildIgnoreLastNameComparision childA = new ChildIgnoreLastNameComparision();
        childA.setFirstName("The Man");
        childA.setLastName("With No Name "+System.nanoTime());

        ChildIgnoreLastNameComparision childB = new ChildIgnoreLastNameComparision();
        childB.setFirstName(childA.getFirstName());
        childB.setLastName("Blondie "+System.nanoTime());
        /** The last name is different, but it will be ignore because {@link ChildIgnoreLastNameComparision#equals(Object)} ignored it.*/

        ParentWithReflectionEquals parentA = new ParentWithReflectionEquals();
        parentA.setName("aaa");
        parentA.setChildEntity(childA);

        ParentWithReflectionEquals parentB = new ParentWithReflectionEquals();
        parentB.setName(parentA.getName());
        parentB.setChildEntity(childB);

        Assert.assertEquals(parentA, parentB);
    }
}
