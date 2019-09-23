package org.tnmk.practicespringjpa.pro02onetomany.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ChildEntity;
import org.tnmk.practicespringjpa.pro02onetomany.sample.entity.ParentEntity;

import java.util.List;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    /**
     * FIXME For now, when executing this method, if there are 3 items in DB, it will execute 3 delete queries: `delete from practice_spring_jpa_db.child_entity where child_id=?`
     *          View more in test case {@link ParentStoryTest#test_UpdateParentAndChildren_Success}.
     * @param parentId
     */
    void deleteByParentId(Long parentId);
}
