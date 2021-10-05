package org.springext.security.jwt.dto;

public enum UserRegistrationResultMessage {
    USER_EXISTS,
    PASSWORDS_DO_NOT_MATCH,
    OK,
    INVALID_CONFIRM_TICKET,
    REGISTRATION_CONFIRMED
}
