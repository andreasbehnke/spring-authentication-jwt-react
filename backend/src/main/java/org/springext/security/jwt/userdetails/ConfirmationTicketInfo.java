package org.springext.security.jwt.userdetails;

public class ConfirmationTicketInfo {

    private final String ticketId;

    private final String recipientEmail;

    public ConfirmationTicketInfo(String ticketId, String recipientEmail) {
        this.ticketId = ticketId;
        this.recipientEmail = recipientEmail;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }
}
