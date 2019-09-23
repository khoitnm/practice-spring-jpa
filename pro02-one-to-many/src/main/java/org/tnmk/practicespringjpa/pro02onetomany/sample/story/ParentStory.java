package org.tnmk.practicespringjpa.pro02onetomany.sample.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ChildRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ParentRepository;
import sun.security.provider.PolicySpiFile;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;


@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class ParentStory {
    public static final String NAME_OF_FAIL_PARENT_SAVE = "Parent_SaveFail";
    public static final String NAME_OF_FAIL_CHILD_DELETION = "Child_DeleteFail";
    public static final String NAME_OF_FAIL_CHILD_SAVE = "Child_SaveFail";



    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    public ParentEntity findParentAndChildren(Long parentId){
        return parentRepository.findParentAndChildrenByParentId(parentId);
    }

    public ParentEntity createParentAndChildren(ParentEntity parentEntity) {
        logger.info("Create Parent and Children -----------------------------------");
        ParentEntity savedParentEntity = parentRepository.save(parentEntity);
        List<ChildEntity> childEntities = parentEntity.getChildren();
        childEntities.forEach(child -> child.setParentId(savedParentEntity.getParentId()));
        childRepository.saveAll(childEntities);
        return parentEntity;
    }

    public ParentEntity createParentOnly(ParentEntity parentEntity) {
        return parentRepository.save(parentEntity);
    }

    public ParentEntity updateParentOnly(ParentEntity parentEntity) {
        return parentRepository.save(parentEntity);
    }

    public ParentEntity updateParentAndChildren(ParentEntity parentEntity) {
        logger.info("Update Parent and Children -----------------------------------");
        ParentEntity savedParentEntity = parentRepository.save(parentEntity);
        List<ChildEntity>  savedChildren = updateChildrenOfParent(savedParentEntity.getParentId(), parentEntity.getChildren());
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
        if (children.stream().filter(child -> child.getName().equals(NAME_OF_FAIL_CHILD_DELETION)).findAny().isPresent()){
            throw new IllegalStateException("Cannot delete children because has one wrong child");
        }
        childRepository.deleteByParentId(parentId);

        children.forEach(child -> child.setParentId(parentId));
        if (children.stream().filter(child -> child.getName().equals(NAME_OF_FAIL_CHILD_SAVE)).findAny().isPresent()){
            throw new IllegalStateException("Cannot save children because has one wrong child");
        }
        return childRepository.saveAll(children);
    }

    public void deleteParentAndChildren(Long parentId) {
        //TODO
    }

    public void deleteChild(Long parentId, Long childId) {

    }
}
