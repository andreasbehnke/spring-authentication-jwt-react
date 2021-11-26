package org.springext.security.jwt.dto;

public class UserRegistrationResult {

    private final UserRegistrationResultMessage message;

    private final boolean isError;

    private String userName;

    public static UserRegistrationResult ok() {
        return new UserRegistrationResult(UserRegistrationResultMessage.OK, false);
    }

    public static UserRegistrationResult userExists() {
        return new UserRegistrationResult(UserRegistrationResultMessage.USER_EXISTS, true);
    }

    public static UserRegistrationResult passwordDoNotMatch() {
        return new UserRegistrationResult(UserRegistrationResultMessage.PASSWORDS_DO_NOT_MATCH, true);
    }

    public static UserRegistrationResult invalidRegistration() {
        return new UserRegistrationResult(UserRegistrationResultMessage.INVALID_REGISTRATION, true);
    }

    public static UserRegistrationResult invalidConfirmTicket() {
        return new UserRegistrationResult(UserRegistrationResultMessage.INVALID_CONFIRM_TICKET, true);
    }

    public static UserRegistrationResult registrationConfirmed(String userName) {
        return new UserRegistrationResult(UserRegistrationResultMessage.REGISTRATION_CONFIRMED, false, userName);
    }

    private UserRegistrationResult(UserRegistrationResultMessage message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    private UserRegistrationResult(UserRegistrationResultMessage message, boolean isError, String userName) {
        this.userName = userName;
        this.message = message;
        this.isError = isError;
    }

    public UserRegistrationResultMessage getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isError() {
        return isError;
    }
}
