package org.tnmk.practicespringjpa.pro02onetomany.sample.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;

@Transactional
@Repository
public class ParentAndChildRepository {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    public ParentEntity createParentAndChildren(ParentEntity parentEntity) {
        logger.info("Create Parent and Children -----------------------------------");
        ParentEntity savedParentEntity = parentRepository.save(parentEntity);
        List<ChildEntity> childEntities = parentEntity.getChildren();
        childEntities.forEach(child -> child.setParentId(savedParentEntity.getParentId()));
        childRepository.saveAll(childEntities);
        return parentEntity;
    }

    public ParentEntity updateParentAndChildren(ParentEntity parentEntity) {
        logger.info("Update Parent and Children -----------------------------------");
        ParentEntity savedParentEntity = parentRepository.save(parentEntity);
        List<ChildEntity> savedChildren = updateChildrenOfParent(savedParentEntity.getParentId(), parentEntity.getChildren());
        savedParentEntity.setChildren(savedChildren);
        return savedParentEntity;
    }

    /**
     * @param parentId
     * @param children this new list of children will replace the old children in DB.
     *                 The children items don't need to keep any reference to parent object/id.
     * @return
     */
    public List<ChildEntity> updateChildrenOfParent(Long parentId, List<ChildEntity> children) {
        childRepository.deleteByParentId(parentId);
        children.forEach(child -> child.setParentId(parentId));
        return childRepository.saveAll(children);
    }

}
