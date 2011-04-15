package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * Domain object representing a custom subcategory.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class CustomSubCategory extends AbstractEntity<Long> implements Serializable {
    private static final long serialVersionUID = 2904913321117627566L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "usd", column = @Column(name = "usd")),
            @AttributeOverride(name = "pivotal", column = @Column(name = "pivotal")),
            @AttributeOverride(name = "mbox", column = @Column(name = "mbox")),
            @AttributeOverride(name = "activeUsd", column = @Column(name = "active_usd")),
            @AttributeOverride(name = "activePivotal", column = @Column(name = "active_pivotal")),
            @AttributeOverride(name = "activeMbox", column = @Column(name = "active_mbox")),
            @AttributeOverride(name = "activeBackend", column = @Column(name = "active_backend"))
    })
    private Backend backend;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Backend getBackend() {
        return backend;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }
}
