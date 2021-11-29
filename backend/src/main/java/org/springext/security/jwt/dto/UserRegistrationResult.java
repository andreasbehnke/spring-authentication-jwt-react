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
        return new UserRegistrationResult(userName);
    }

    public static UserRegistrationResult invalidPasswordResetTicket() {
        return new UserRegistrationResult(UserRegistrationResultMessage.INVALID_PASSWORD_RESET_TICKET, true);
    }

    public static UserRegistrationResult invalidPasswordReset() {
        return new UserRegistrationResult(UserRegistrationResultMessage.INVALID_PASSWORD_RESET, true);
    }

    private UserRegistrationResult(UserRegistrationResultMessage message, boolean isError) {
        this.message = message;
        this.isError = isError;
    }

    private UserRegistrationResult(String userName) {
        this.userName = userName;
        this.message = UserRegistrationResultMessage.REGISTRATION_CONFIRMED;
        this.isError = false;
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
