package org.tnmk.common.comparision;

import org.junit.Assert;
import org.junit.Test;

public class ComparisionTest {


    @Test
    public void test_reflectionEquals_will_use_our_equals_implementation_instead_of_blindly_compare_all_fields() {
        ChildEntity childA = new ChildEntity();
        childA.setFirstName("a");
        childA.setLastName("b"+System.nanoTime());

        ChildEntity childB = new ChildEntity();
        childB.setFirstName(childA.getFirstName());
        childB.setLastName("b"+System.nanoTime());
        /** The last name is different, but it will be ignore because {@link ChildEntity#equals(Object)} ignored it.*/

        ParentEntity parentA = new ParentEntity();
        parentA.setName("aaa");
        parentA.setChildEntity(childA);

        ParentEntity parentB = new ParentEntity();
        parentB.setName(parentA.getName());
        parentB.setChildEntity(childB);

        Assert.assertEquals(parentA, parentB);
    }
}
