package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.userfeedback.domain.CustomCategory;
import se.vgregion.userfeedback.domain.FormTemplate;
import se.vgregion.userfeedback.domain.FormTemplateRepository;

import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class MockFormTemplateRepository implements FormTemplateRepository {
    @Override
    public FormTemplate find(String name) {
        if ("default".equals(name)) {
            FormTemplate template = new FormTemplate();
            template.setName("default");
            return template;
        } else if ("test".equals(name)) {
            FormTemplate template = new FormTemplate();
            template.setName("test");
            template.setShowCustom(Boolean.TRUE);
            CustomCategory custom = new CustomCategory();
            custom.setName("TestCustom");
            template.setCustomCategory(custom);
            template.setShowContactByEmail(false);
            template.setShowContactByPhone(true);
            return template;
        }

        throw new NoResultException("No result");
    }

    @Override
    public FormTemplate persist(FormTemplate object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(FormTemplate object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Long aLong) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<FormTemplate> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FormTemplate find(Long aLong) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FormTemplate merge(FormTemplate object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(FormTemplate object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(FormTemplate entity) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FormTemplate store(FormTemplate entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
