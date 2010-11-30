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
@Table
public class FormTemplate extends AbstractEntity<Long> implements Serializable {
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
    private Boolean showCustom;

    private Boolean showContent = Boolean.TRUE;

    private Boolean showFunction = Boolean.TRUE;

    private Boolean showOther = Boolean.TRUE;

    private Boolean showContact = Boolean.TRUE;

    private Boolean showAttachment = Boolean.TRUE;

    @OneToOne(cascade = CascadeType.ALL)
    private CustomCategory customCategory = new CustomCategory();

    @Transient
    private StaticCategory contentCategory = new StaticCategory("Webbplatsens innehåll",
            "Saknar innehåll", "Fel innehåll", "Hittar inte information" );

    @Transient
    private StaticCategory functionCategory = new StaticCategory("Webbplatsens funktion",
            "Sidan finns inte", "Felmeddelande", "Sidan laddas inte", "Förstår inte funktionen");

    @Transient
    private StaticCategory otherCategory = new StaticCategory("Övrigt");


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

    public Boolean getShowCustom() {
        return showCustom;
    }

    public void setShowCustom(Boolean showCustom) {
        this.showCustom = showCustom;
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

    public Boolean getShowOther() {
        return showOther;
    }

    public void setShowOther(Boolean showOther) {
        this.showOther = showOther;
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

    public CustomCategory getCustomCategory() {
        return customCategory;
    }

    public void setCustomCategory(CustomCategory customCategory) {
        this.customCategory = customCategory;
    }

    public StaticCategory getContentCategory() {
        return contentCategory;
    }

    public StaticCategory getFunctionCategory() {
        return functionCategory;
    }

    public StaticCategory getOtherCategory() {
        return otherCategory;
    }
}
