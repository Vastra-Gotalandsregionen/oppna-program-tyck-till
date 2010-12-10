package se.vgregion.userfeedback.validation;

import org.junit.BeforeClass;
import org.junit.Test;
import se.vgregion.userfeedback.domain.UserContact;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public class CheckUserContactValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testEmpty() throws Exception {
        UserContact contact = new UserContact();
        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(0, violations.size());
    }

    @Test
    public void testNoContact() throws Exception {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(false);
        
        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(0, violations.size());

        contact.setUserName("Apa");

        violations = validator.validate(contact);
        assertEquals(0, violations.size());

        contact.setContactOption(UserContact.UserContactOption.email);

        violations = validator.validate(contact);
        assertEquals(0, violations.size());

        contact.setContactMethod("12345");

        violations = validator.validate(contact);
        assertEquals(0, violations.size());
    }

    @Test
    public void testNoContactInfo() {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(true);

        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(2, violations.size());

        for (Iterator<ConstraintViolation<UserContact>> it = violations.iterator(); it.hasNext(); ) {
            ConstraintViolation<UserContact> violation = it.next();
            if (violation.getPropertyPath().toString().equals("userName")) {
                assertEquals("{vgr.tycktill.usercontact.username}", violation.getMessageTemplate());
                it.remove();
            }
            if (violation.getPropertyPath().toString().equals("contactMethod")) {
                assertEquals("{vgr.tycktill.usercontact.contactmethod}", violation.getMessageTemplate());
                it.remove();
            }
        }
        assertEquals(0, violations.size());
    }

    @Test
    public void testNoUserName() {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(true);
        contact.setContactMethod("Apa");

        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(1, violations.size());

        ConstraintViolation<UserContact> violation = violations.iterator().next();
        assertEquals("userName", violation.getPropertyPath().toString());
        assertEquals("{vgr.tycktill.usercontact.username}", violation.getMessageTemplate());
    }

    @Test
    public void testNoContactMethodNoContactOption() {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(true);
        contact.setUserName("Apa");

        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(1, violations.size());

        ConstraintViolation<UserContact> violation = violations.iterator().next();
        assertEquals("contactMethod", violation.getPropertyPath().toString());
        assertEquals("{vgr.tycktill.usercontact.contactmethod}", violation.getMessageTemplate());
    }

    @Test
    public void testEmailNoContactMethod() {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(true);
        contact.setUserName("Apa");
        contact.setContactOption(UserContact.UserContactOption.email);

        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(1, violations.size());

        ConstraintViolation<UserContact> violation = violations.iterator().next();
        assertEquals("contactMethod", violation.getPropertyPath().toString());
        assertEquals("{vgr.tycktill.usercontact.contactmethod.email}", violation.getMessageTemplate());
    }

    @Test
    public void testTelephoneNoContactMethod() {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(true);
        contact.setUserName("Apa");
        contact.setContactOption(UserContact.UserContactOption.telephone);

        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(1, violations.size());

        ConstraintViolation<UserContact> violation = violations.iterator().next();
        assertEquals("contactMethod", violation.getPropertyPath().toString());
        assertEquals("{vgr.tycktill.usercontact.contactmethod.telephone}", violation.getMessageTemplate());
    }

    @Test
    public void testOK() {
        UserContact contact = new UserContact();
        contact.setShouldContactUser(true);
        contact.setUserName("Apa");
        contact.setContactOption(UserContact.UserContactOption.telephone);
        contact.setContactMethod("123456");

        Set<ConstraintViolation<UserContact>> violations = validator.validate(contact);
        assertEquals(0, violations.size());
    }
}
