/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */

package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.userfeedback.domain.UserFeedback;
import se.vgregion.userfeedback.domain.UserFeedbackRepository;

import java.util.Collection;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class MockUserFeedbackRepository implements UserFeedbackRepository {
    @Override
    public UserFeedback persist(UserFeedback object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void flush() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(UserFeedback object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void remove(Long aLong) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<UserFeedback> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserFeedback find(Long aLong) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserFeedback merge(UserFeedback object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refresh(UserFeedback object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(UserFeedback entity) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserFeedback store(UserFeedback entity) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
