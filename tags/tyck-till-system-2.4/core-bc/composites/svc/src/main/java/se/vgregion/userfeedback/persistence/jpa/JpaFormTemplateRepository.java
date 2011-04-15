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
