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

    private String defaultContact;

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

    public String getDefaultContact() {
        return defaultContact;
    }

    public void setDefaultContact(String defaultContact) {
        this.defaultContact = defaultContact;
    }

    public List<CustomSubCategory> getCustomSubCategories() {
        return customSubCategories;
    }

    public void setCustomSubCategories(List<CustomSubCategory> customSubCategories) {
        this.customSubCategories = customSubCategories;
    }
}
