package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.repository.Repository;

/**
 * Repository interface for handling FormTemplates.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface FormTemplateRepository extends Repository<FormTemplate, Long> {

    public FormTemplate find(String name);

}
