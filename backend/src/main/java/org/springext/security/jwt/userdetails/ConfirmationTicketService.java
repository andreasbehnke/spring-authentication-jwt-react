package org.springext.security.jwt.userdetails;

public interface ConfirmationTicketService {

    void sendConfirmationTicket(ConfirmationTicketInfo confirmationTicketInfo);

    void sendForgotPasswordTicket(ConfirmationTicketInfo confirmationTicketInfo);
}
