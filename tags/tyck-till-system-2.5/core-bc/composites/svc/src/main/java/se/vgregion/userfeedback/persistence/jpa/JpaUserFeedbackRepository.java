package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.userfeedback.domain.UserFeedback;
import se.vgregion.userfeedback.domain.UserFeedbackRepository;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaUserFeedbackRepository extends DefaultJpaRepository<UserFeedback> implements UserFeedbackRepository {
}
