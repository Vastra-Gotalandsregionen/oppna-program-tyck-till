package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.repository.Repository;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface FormTemplateRepository extends Repository<FormTemplate, Long> {

    public FormTemplate find(String name);

}
