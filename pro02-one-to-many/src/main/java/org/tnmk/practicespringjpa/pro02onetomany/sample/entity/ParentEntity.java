package org.tnmk.practicespringjpa.pro02onetomany.sample.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parent_entity", catalog = "sample_db")
public class ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "child_id")
    private List<ChildEntity> children;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildEntity> children) {
        this.children = children;
    }
}
