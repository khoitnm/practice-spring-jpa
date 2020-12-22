package org.tnmk.practicespringjpa.pro01simpleentityuuidfield.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    /**
     * By default, Spring will store UUID field as BINARY(255).
     * However, we won't be able to execute `findById()`, it will return null value.
     * That's why we have to config BINARY(16)
     */
    @Id
    @Column(name = "person_id", nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    /**
     * By default, Spring will store UUID field as BINARY(255).
     * However, we won't be able to execute `findBySomeAssociatedId()`, it will return item list.
     * That's why we have to config BINARY(16)
     */
    @Column(name = "some_associated_id", columnDefinition = "BINARY(16)")
    private UUID someAssociatedId;

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", fullName='" + fullName + '\'' +
            ", someAssociatedId=" + someAssociatedId +
            '}';
    }

    public UUID getSomeAssociatedId() {
        return someAssociatedId;
    }

    public void setSomeAssociatedId(UUID someAssociatedId) {
        this.someAssociatedId = someAssociatedId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID personId) {
        this.id = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
