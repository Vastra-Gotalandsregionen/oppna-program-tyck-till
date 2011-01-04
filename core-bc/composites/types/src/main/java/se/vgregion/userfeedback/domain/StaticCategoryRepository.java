package se.vgregion.userfeedback.domain;

import se.vgregion.dao.domain.patterns.repository.Repository;

/**
 * Interface for the repository that that handles static categories.
 * It also declares which static categories exist.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface StaticCategoryRepository extends Repository<StaticCategory, Long> {
    /**
     * The content StaticCategory id.
     */
    static Long STATIC_CONTENT_CATEGORY = -1L;
    /**
     * The function StaticCategory id.
     */
    static Long STATIC_FUNCTION_CATEGORY = -2L;
    /**
     * The other StaticCategory id.
     */
    static Long STATIC_OTHER_CATEGORY = -3L;

}
