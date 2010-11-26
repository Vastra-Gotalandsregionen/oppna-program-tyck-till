package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class CustomSubCategory extends AbstractEntity<CustomSubCategory, Long> implements Serializable {
    private static final long serialVersionUID = 2904913321117627566L;

    @Id
    @GeneratedValue
    @Column(name = "customsubcategory_id")
    private Long customSubCategoryId;

    private String name;

    private String contact;

    @ManyToOne
    private CustomCategory customCategory;

    @Override
    public Long getId() {
        return customSubCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public CustomCategory getCustomCategory() {
        return customCategory;
    }

    public void setCustomCategory(CustomCategory customCategory) {
        this.customCategory = customCategory;
    }
}
