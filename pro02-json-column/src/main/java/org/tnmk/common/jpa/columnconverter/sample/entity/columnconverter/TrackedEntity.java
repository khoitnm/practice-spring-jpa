package org.tnmk.common.jpa.columnconverter.sample.entity.columnconverter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.tnmk.common.jpa.columnconverter.datetime.InstantTimeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class TrackedEntity {
    protected TrackedEntity() {
        // The abstract class does not contain any abstract methods. An abstract class suggests
        // an incomplete implementation, which is to be completed by subclasses implementing the
        // abstract methods. If the class is intended to be used as a base class only (not to be instantiated
        // directly) a protected constructor can be provided prevent direct instantiation.
    }

    @Convert(converter = InstantTimeConverter.class)
    @CreationTimestamp
    @Column(name = "creation_timestamp", updatable = false, columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Instant creationTimestamp;

    @Convert(converter = InstantTimeConverter.class)
    @UpdateTimestamp
    @Column(name = "update_timestamp", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Instant updateTimestamp;

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Instant getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Instant updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
