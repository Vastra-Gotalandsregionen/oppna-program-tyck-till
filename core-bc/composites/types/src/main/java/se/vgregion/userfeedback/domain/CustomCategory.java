package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class CustomCategory extends AbstractEntity<Integer> implements Serializable {
    private static final long serialVersionUID = 2103137794928299880L;

    @Id
    @GeneratedValue
    private Integer id;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "customcategory_id")
    @OrderColumn(name = "index")
    private List<CustomSubCategory> customSubCategories = new ArrayList<CustomSubCategory>();

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomSubCategory> getCustomSubCategories() {
        return customSubCategories;
    }

    public void setCustomSubCategories(List<CustomSubCategory> customSubCategories) {
        this.customSubCategories = customSubCategories;
    }

    public Backend getBackend() {
        return backend;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }
}
