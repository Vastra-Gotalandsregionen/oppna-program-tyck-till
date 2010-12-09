package se.vgregion.userfeedback.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUserContactValidator.class)
@Documented
public @interface CheckUserContact {

    String message() default "{vgr.tycktill.usercontact}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
