package org.tnmk.common.jpa.columnconverter.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.jpa.columnconverter.json.factory.ParentFactory;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ParentRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.story.ParentStory;

public class ParentStoryTest extends BaseTest {

    @Autowired
    ParentStory parentStory;

    @Autowired
    ParentRepository parentRepository;

    @Disabled
    @Test
    public void testCreateParentOnly() {
        ParentEntity parent = ParentFactory.constructParentAndChildren(3);

        ParentEntity savedParent = parentStory.createParentOnly(parent);

        ParentEntity foundParent = parentRepository.findParentAndChildrenByParentId(savedParent.getParentId());
        Assert.assertNotNull(foundParent);
        Assert.assertTrue(foundParent.getChildren() == null || foundParent.getChildren().isEmpty());
    }
}
