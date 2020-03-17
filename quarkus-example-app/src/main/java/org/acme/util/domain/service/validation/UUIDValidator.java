package org.acme.util.domain.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UUIDValidator implements ConstraintValidator<ValidUUID, String> {

    public static final String UUID_PATTERN = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";
    private Pattern pattern;

    @Override
    public void initialize(ValidUUID constraint) {
        pattern = Pattern.compile(UUID_PATTERN);
    }

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        return pattern.matcher(uuid).matches();
    }
}
