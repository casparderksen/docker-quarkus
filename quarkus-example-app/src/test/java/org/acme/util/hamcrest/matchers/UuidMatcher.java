package org.acme.util.hamcrest.matchers;

import org.acme.util.domain.service.validation.UUIDValidator;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class UuidMatcher extends TypeSafeMatcher<String> {

    private final UUIDValidator validator;

    public UuidMatcher() {
        validator = new UUIDValidator();
        validator.initialize(null);
    }

    @Override
    protected boolean matchesSafely(String uuid) {
        return validator.isValid(uuid, null);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue("is a valid UUID");
    }

    public static UuidMatcher isValidUUID() {
        return new UuidMatcher();
    }
}
