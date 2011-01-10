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

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.userfeedback.domain.FormTemplate;
import se.vgregion.userfeedback.domain.FormTemplateRepository;

import javax.persistence.Query;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaFormTemplateRepository extends DefaultJpaRepository<FormTemplate> implements FormTemplateRepository {
    @Override
    public FormTemplate find(String name) {
        String queryString = "SELECT ft FROM FormTemplate ft WHERE ft.name = :name";
        
        Query query = entityManager.createQuery(queryString).setParameter("name", name);
        return (FormTemplate) query.getSingleResult();
    }
}
