package org.springext.security.jwt.dto;

public class UserRegistrationResult {

    private final UserRegistrationResultMessage message;

    public static UserRegistrationResult ok() {
        return new UserRegistrationResult(UserRegistrationResultMessage.OK);
    }

    public static UserRegistrationResult userExists() {
        return new UserRegistrationResult(UserRegistrationResultMessage.USER_EXISTS);
    }

    public static UserRegistrationResult passwordDoNotMatch() {
        return new UserRegistrationResult(UserRegistrationResultMessage.PASSWORDS_DO_NOT_MATCH);
    }

    private UserRegistrationResult(UserRegistrationResultMessage message) {
        this.message = message;
    }

    public UserRegistrationResultMessage getMessage() {
        return message;
    }
}
