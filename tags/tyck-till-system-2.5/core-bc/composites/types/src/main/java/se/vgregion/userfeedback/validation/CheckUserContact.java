package se.vgregion.userfeedback.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JSR-303 annotation for UserContact validation.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUserContactValidator.class)
@Documented
public @interface CheckUserContact {

    /**
     * Declaring key for default error message lookup.
     *
     * @return  lookup key.
     */
    String message() default "{vgr.tycktill.usercontact}";

    /**
     * No group behaviour.
     *
     * @return empty.
     */
    Class<?>[] groups() default { };

    /**
     * No payload.
     *
     * @return empty.
     */
    Class<? extends Payload>[] payload() default { };

}
