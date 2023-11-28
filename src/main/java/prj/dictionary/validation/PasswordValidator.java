package prj.dictionary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import prj.dictionary.constant.IConstant;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            return true;
        }

        if (password.length() < 8) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must have at least 8 characters").addConstraintViolation();
            return false;
        }

        if (!password.matches(IConstant.PASSWORD_REGEX)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must have at least one uppercase letter, one lowercase letter, and one number").addConstraintViolation();
            return false;
        }

        return true;
    }
}
