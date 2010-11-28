package se.vgregion.userfeedback.domain;

import org.hibernate.validator.constraints.NotEmpty;
import se.vgregion.dao.domain.patterns.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class FormTemplate extends AbstractEntity<FormTemplate, Long> implements Serializable {
    private static final long serialVersionUID = 7819565362034276611L;

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version;

    @NotEmpty(message = "{template.name}")
    @Column(unique = true, nullable = false)
    private String name;

    @Size(min = 5, max = 50, message = "{template.title}")
    private String title;

    @Column(length = 2048)
    @Size(max = 2048, message = "{template.description}")
    private String description;

    @NotEmpty(message = "{template.backend}")
    private String backend;

    private String lastChangedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChanged = new Date();


    // optional parts
    private Boolean showHeathcareSubject;

    private Boolean showContent = Boolean.TRUE;

    private Boolean showFunction = Boolean.TRUE;

    private Boolean showContact = Boolean.TRUE;

    private Boolean showAttachment = Boolean.TRUE;

    @OneToOne
    private CustomCategory customCategory;

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

    public Boolean getShowContent() {
        return showContent;
    }

    public void setShowContent(Boolean showContent) {
        this.showContent = showContent;
    }

    public Boolean getShowFunction() {
        return showFunction;
    }

    public void setShowFunction(Boolean showFunction) {
        this.showFunction = showFunction;
    }

    public Boolean getShowContact() {
        return showContact;
    }

    public void setShowContact(Boolean showContact) {
        this.showContact = showContact;
    }

    public Boolean getShowAttachment() {
        return showAttachment;
    }

    public void setShowAttachment(Boolean showAttachment) {
        this.showAttachment = showAttachment;
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
