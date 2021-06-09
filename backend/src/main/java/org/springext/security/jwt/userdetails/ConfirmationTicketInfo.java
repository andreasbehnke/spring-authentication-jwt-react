package org.springext.security.jwt.userdetails;

public class ConfirmationTicketInfo<U extends UserAuthenticationDetails> {

    private final String ticketId;

    private final String recipientEmail;

    private final U userAuthenticationDetails;

    public ConfirmationTicketInfo(String ticketId, String recipientEmail, U userAuthenticationDetails) {
        this.ticketId = ticketId;
        this.recipientEmail = recipientEmail;
        this.userAuthenticationDetails = userAuthenticationDetails;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public U getUserAuthenticationDetails() {
        return userAuthenticationDetails;
    }
}
