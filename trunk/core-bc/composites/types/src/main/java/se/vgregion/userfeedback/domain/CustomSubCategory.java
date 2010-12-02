package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class CustomSubCategory extends AbstractEntity<Long> implements Serializable {
    private static final long serialVersionUID = 2904913321117627566L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String contact;

    @Embedded
    private Backend subCategoryBackend;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
