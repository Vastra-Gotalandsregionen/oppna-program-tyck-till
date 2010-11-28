package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.entity.*;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Entity
public class Attachment extends AbstractEntity<Attachment, Long> implements se.vgregion.dao.domain.patterns.entity.Entity<Attachment,Long> {
    private static final long serialVersionUID = -3136723460452765682L;

    @Id
    @GeneratedValue
    private Long id;

    private String filename;

    @Lob
    private byte[] file;

    @Override
    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
