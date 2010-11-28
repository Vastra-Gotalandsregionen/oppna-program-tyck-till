package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class CustomCategory extends AbstractEntity<CustomCategory, Long> implements Serializable {
    private static final long serialVersionUID = 2103137794928299880L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customcategory_id")
    @OrderColumn(name = "index")
    private List<CustomSubCategory> customSubCategories;

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

    public List<CustomSubCategory> getCustomSubCategories() {
        return customSubCategories;
    }

    public void setCustomSubCategories(List<CustomSubCategory> customSubCategories) {
        this.customSubCategories = customSubCategories;
    }
}
