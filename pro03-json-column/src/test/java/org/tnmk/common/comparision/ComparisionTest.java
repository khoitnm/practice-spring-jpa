package org.tnmk.common.comparision;

import org.junit.Assert;
import org.junit.Test;

public class ComparisionTest {


    @Test
    public void test() {
        ChildComparisionEntity childA = new ChildComparisionEntity();
        childA.setFieldA("a");
        childA.setFieldB("b"+System.nanoTime());

        ChildComparisionEntity childB = new ChildComparisionEntity();
        childB.setFieldA(childA.getFieldA());
        childB.setFieldB("b"+System.nanoTime());

        ParentEntity parentA = new ParentEntity();
        parentA.setName("aaa");
        parentA.setChildComparisionEntity(childA);

        ParentEntity parentB = new ParentEntity();
        parentB.setName(parentA.getName());
        parentB.setChildComparisionEntity(childB);

        Assert.assertEquals(parentA, parentB);
    }
}
