package prj.dictionary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import prj.dictionary.constant.IConstant;

public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;
        }

        if (!email.matches(IConstant.EMAIL_REGEX)) {
            return false;
        }

        return true;
    }
}
