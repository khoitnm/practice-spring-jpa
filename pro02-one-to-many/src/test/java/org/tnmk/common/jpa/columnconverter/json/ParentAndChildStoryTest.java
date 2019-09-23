package org.tnmk.common.jpa.columnconverter.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.jpa.columnconverter.json.factory.ChildFactory;
import org.tnmk.common.jpa.columnconverter.json.factory.ParentFactory;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ParentRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.story.ParentAndChildStory;

import java.util.List;

@Disabled
public class ParentAndChildStoryTest extends BaseTest {

    @Autowired
    ParentAndChildStory parentStory;

    @Autowired
    ParentRepository parentRepository;

    @Test
    public void test_FindParentsAndChildren_GroupByParents() {
        ParentEntity parent1 = ParentFactory.constructParentAndChildren("CustomParent1", 1);
        parentStory.createParentAndChildren(parent1);

        ParentEntity parent2 = ParentFactory.constructParentAndChildren("CustomParent2", 2);
        parentStory.createParentAndChildren(parent2);

        ParentEntity parent3 = ParentFactory.constructParentAndChildren("CustomParent3", 3);
        parentStory.createParentAndChildren(parent3);


        List<ParentEntity> foundParents = parentRepository.findParentsAndChildrenByLikeParentName("CustomParent");
        Assert.assertEquals(3, foundParents.size());
    }

    @Test
    public void testCreateParentOnly() {
        ParentEntity parent = ParentFactory.constructParentAndChildren(3);

        ParentEntity savedParent = parentStory.createParentOnly(parent);

        ParentEntity foundParent = parentRepository.findParentAndChildrenByParentId(savedParent.getParentId());
        Assert.assertNotNull(foundParent);
        Assert.assertTrue(foundParent.getChildren().isEmpty());
    }

    @Test
    public void testCreateParentAndChildren() {
        int numOfChildren = 3;
        ParentEntity parent = ParentFactory.constructParentAndChildren(numOfChildren);

        ParentEntity savedParent = parentStory.createParentAndChildren(parent);

        ParentEntity foundParent = parentRepository.findParentAndChildrenByParentId(savedParent.getParentId());

        Assert.assertNotNull(foundParent);
        Assert.assertEquals(numOfChildren, foundParent.getChildren().size());
    }

    @Test
    public void test_UpdateParentAndChildren_Success() {
        //CREATE
        int numNewChildren = 3;
        ParentEntity parent = ParentFactory.constructParentAndChildren(numNewChildren);

        ParentEntity savedNewParent = parentStory.createParentAndChildren(parent);
        ParentEntity foundNewParent = parentRepository.findParentAndChildrenByParentId(savedNewParent.getParentId());


        //UPDATE
        int numUpdateChildren = 2;
        String updateParentName = "Updated_" + System.nanoTime();
        parent.setName(updateParentName);
        List<ChildEntity> newChildren = ChildFactory.constructChildren(numUpdateChildren);
        parent.setChildren(newChildren);
        parentStory.updateParentAndChildren(parent);

        ParentEntity foundUpdateParent = parentRepository.findParentAndChildrenByParentId(savedNewParent.getParentId());

        //ASSERT
        Assert.assertEquals(updateParentName, foundUpdateParent.getName());
        Assert.assertNotEquals(foundNewParent.getName(), foundUpdateParent.getName());

        Assert.assertEquals(numUpdateChildren, foundUpdateParent.getChildren().size());
    }

    @Test
    public void test_UpdateParentAndChildren_RollbackWhenFail() {
        //CREATE
        int numNewChildren = 3;
        ParentEntity parent = ParentFactory.constructParentAndChildren(numNewChildren);

        ParentEntity savedNewParent = parentStory.createParentAndChildren(parent);
        ParentEntity foundNewParent = parentRepository.findParentAndChildrenByParentId(savedNewParent.getParentId());
        List<ChildEntity> savedNewChildren = foundNewParent.getChildren();


        //UPDATE
        int numUpdateChildren = 2;
        String updateParentName = "Updated_" + System.nanoTime();
        parent.setName(updateParentName);
        List<ChildEntity> updateChildren = ChildFactory.constructChildren(numUpdateChildren);
        updateChildren.get(0).setName(null);//This will make saving children fail
        parent.setChildren(updateChildren);

        try {
            parentStory.updateParentAndChildren(parent);
            Assert.assertFalse("The test case should throw exception instead of success!", true);
        } catch (Exception ex) {
            ParentEntity foundUpdateParent = parentRepository.findParentAndChildrenByParentId(savedNewParent.getParentId());

            //ASSERT
            //The parent name shouldn't be updated
            Assert.assertNotEquals(updateParentName, foundUpdateParent.getName());
            Assert.assertEquals(foundNewParent.getName(), foundUpdateParent.getName());

            //The children list shouldn't be updated
            Assert.assertTrue(!foundUpdateParent.getChildren().isEmpty());
            Assert.assertEquals(savedNewChildren.size(), foundUpdateParent.getChildren().size());
            for (ChildEntity updatedChild : foundUpdateParent.getChildren()) {
                Assert.assertTrue(
                    savedNewChildren.stream()
                        .filter(savedNewChild ->
                            savedNewChild.getName().equals(updatedChild.getName())
                            && savedNewChild.getChildId().equals(updatedChild.getChildId())
                        )
                        .findAny().isPresent()
                );
            }
        }


    }
}
