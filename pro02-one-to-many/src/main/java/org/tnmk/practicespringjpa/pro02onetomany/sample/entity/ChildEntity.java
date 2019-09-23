package org.tnmk.practicespringjpa.pro02onetomany.sample.entity;

import javax.persistence.*;

@Entity
@Table(name = "child_entity", catalog = "practice_spring_jpa_db")
public class ChildEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "child_id")
    private Long childId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
