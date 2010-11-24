package se.vgregion.userfeedback.domain;

import org.hibernate.validator.constraints.NotEmpty;
import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(name = "vgr_tycktill_form")
public class FormTemplate extends AbstractEntity<FormTemplate, Long> {

    @Id
    @GeneratedValue
    private Long formTemplateId;

    @Version
    private Integer version;

    @NotEmpty(message = "{template.name}")
    private String name;
    
    @Size(min = 5, max = 50, message = "{template.title}")
    private String title;

    @Column(length = 2048)
    @Size(max = 2048, message = "{template.description}")
    private String description;

    private Boolean showHeathcareSubject;

    @NotEmpty(message = "{template.backend}")
    private String backend;

    private String lastChangedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChanged = new Date();

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

    public Long getFormTemplateId() {
        return formTemplateId;
    }

    public void setFormTemplateId(Long formTemplateId) {
        this.formTemplateId = formTemplateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getShowHeathcareSubject() {
        return showHeathcareSubject;
    }

    public void setShowHeathcareSubject(Boolean showHeathcareSubject) {
        this.showHeathcareSubject = showHeathcareSubject;
    }

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getLastChangedBy() {
        return lastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        this.lastChangedBy = lastChangedBy;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }
}
