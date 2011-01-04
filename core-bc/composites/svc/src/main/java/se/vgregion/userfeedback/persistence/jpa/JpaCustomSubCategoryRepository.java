package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.userfeedback.domain.CustomSubCategory;
import se.vgregion.userfeedback.domain.CustomSubCategoryRepository;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaCustomSubCategoryRepository extends DefaultJpaRepository<CustomSubCategory> implements CustomSubCategoryRepository {
}
