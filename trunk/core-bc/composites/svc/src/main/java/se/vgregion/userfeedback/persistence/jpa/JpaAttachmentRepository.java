package se.vgregion.userfeedback.persistence.jpa;

import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;
import se.vgregion.userfeedback.domain.Attachment;
import se.vgregion.userfeedback.domain.AttachmentRepository;

/**
 * Implementation of the Attachment repository.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class JpaAttachmentRepository extends DefaultJpaRepository<Attachment> implements AttachmentRepository {
}
