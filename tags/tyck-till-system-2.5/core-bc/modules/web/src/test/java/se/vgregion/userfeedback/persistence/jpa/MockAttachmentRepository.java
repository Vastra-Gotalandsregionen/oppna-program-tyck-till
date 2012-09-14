package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.userfeedback.domain.Attachment;
import se.vgregion.userfeedback.domain.AttachmentRepository;

import java.util.Collection;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class MockAttachmentRepository implements AttachmentRepository {
    @Override
    public Attachment persist(Attachment object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Attachment object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Long aLong) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Attachment> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Attachment find(Long aLong) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Attachment merge(Attachment object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(Attachment object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(Attachment entity) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Attachment store(Attachment entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
