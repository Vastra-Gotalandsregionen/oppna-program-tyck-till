package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class FormTemplate extends AbstractEntity<FormTemplate, Long> {

    @Id
    @GeneratedValue
    private Long formTemplateId;

    private String name;


    @Override
    public Long getId() {
        return formTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
