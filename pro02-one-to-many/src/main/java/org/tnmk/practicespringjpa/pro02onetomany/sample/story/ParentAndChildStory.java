package org.tnmk.practicespringjpa.pro02onetomany.sample.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ChildRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ParentAndChildRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ParentRepository;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;


@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class ParentAndChildStory {


    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private ParentAndChildRepository parentAndChildRepository;

    public ParentEntity findParentAndChildren(Long parentId){
        return parentRepository.findParentAndChildrenByParentId(parentId);
    }

    public ParentEntity createParentAndChildren(ParentEntity parentEntity) {
        return parentAndChildRepository.createParentAndChildren(parentEntity);
    }

    public ParentEntity createParentOnly(ParentEntity parentEntity) {
        return parentRepository.save(parentEntity);
    }

    public ParentEntity updateParentOnly(ParentEntity parentEntity) {
        return parentRepository.save(parentEntity);
    }

    public ParentEntity updateParentAndChildren(ParentEntity parentEntity) {
        return parentAndChildRepository.updateParentAndChildren(parentEntity);
    }


    /**
     * @param parentId
     * @param children this new list of children will replace the old children in DB.
     *                 The children items don't need to keep any reference to parent object/id.
     * @return
     */
    public List<ChildEntity> updateChildrenOfParent(Long parentId, List<ChildEntity> children) {
        return parentAndChildRepository.saveChildrenOfParent(parentId, children);
    }

    public void deleteParentAndChildren(Long parentId) {
        //TODO
    }

    public void deleteChild(Long parentId, Long childId) {
        //TODO
    }
}
