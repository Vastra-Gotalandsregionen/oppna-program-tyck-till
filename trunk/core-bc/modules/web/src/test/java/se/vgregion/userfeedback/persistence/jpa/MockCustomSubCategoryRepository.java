package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.userfeedback.domain.CustomSubCategory;
import se.vgregion.userfeedback.domain.CustomSubCategoryRepository;

import java.util.Collection;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class MockCustomSubCategoryRepository implements CustomSubCategoryRepository {
    @Override
    public CustomSubCategory persist(CustomSubCategory object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(CustomSubCategory object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Long aLong) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<CustomSubCategory> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CustomSubCategory find(Long aLong) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CustomSubCategory merge(CustomSubCategory object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(CustomSubCategory object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(CustomSubCategory entity) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CustomSubCategory store(CustomSubCategory entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
