package org.tnmk.practicespringjpa.pro02onetomany.sample.story;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ChildRepository;
import org.tnmk.practicespringjpa.pro02onetomany.sample.repository.ParentRepository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional // Without this, the service cannot commit transaction, hence cannot create/update/delete items
@Service
public class ParentStory {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    public ParentEntity findParentAndChildren(Long parentId){
        return parentRepository.findParentAndChildrenByParentId(parentId);
    }

    public ParentEntity createParentAndChildren(ParentEntity parentEntity) {
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

    public void updateChildren(Long parentId, List<ChildEntity> children) {

    }

    public void deleteParentAndChildren(Long parentId) {
        //TODO
    }

    public void deleteChild(Long parentId, Long childId) {

    }
}
