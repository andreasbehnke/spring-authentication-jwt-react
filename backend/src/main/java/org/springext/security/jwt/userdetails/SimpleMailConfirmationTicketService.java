package org.springext.security.jwt.userdetails;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

//@ConditionalOnBean(name = "mailSender")
@Service
public class SimpleMailConfirmationTicketService implements ConfirmationTicketService {

    public static final String PLACEHOLDER_TICKET_ID = "{ticketId}";

    private final MailSender mailSender;

    private final String confirmationMailFrom;

    private final String confirmationMailSubject;

    private final String confirmationMailIntroductionText;

    private final String forgotPasswordMailSubject;

    private final String forgotPasswordMailIntroductionText;

    private final String forgotPasswordMailFrom;

    public SimpleMailConfirmationTicketService(RegistrationConfigurationProperties configurationProperties, MailSender mailSender) {
        this.confirmationMailFrom = configurationProperties.getConfirmationMailFrom();
        this.confirmationMailSubject = configurationProperties.getConfirmationMailSubject();
        this.confirmationMailIntroductionText = configurationProperties.getConfirmationMailIntroductionText();
        Assert.notNull(this.confirmationMailFrom, "Required property authentication.registration.confirmation-mail-from is missing");
        Assert.notNull(this.confirmationMailSubject, "Required property authentication.registration.confirmation-mail-subject is missing");
        Assert.notNull(this.confirmationMailIntroductionText, "Required property authentication.registration.confirmation-mail-introduction-text is missing");
        this.forgotPasswordMailFrom = configurationProperties.getForgotPasswordMailFrom();
        this.forgotPasswordMailSubject = configurationProperties.getForgotPasswordMailSubject();
        this.forgotPasswordMailIntroductionText = configurationProperties.getForgotPasswordMailIntroductionText();
        Assert.notNull(this.forgotPasswordMailFrom, "Required property authentication.registration.forgot-password-mail-from is missing");
        Assert.notNull(this.forgotPasswordMailSubject, "Required property authentication.registration.forgot-password-mail-subject is missing");
        Assert.notNull(this.forgotPasswordMailIntroductionText, "Required property authentication.registration.forgot-password-mail-introduction-text is missing");

        this.mailSender = mailSender;
    }

    private void sendMail(String from, String recipient, String subject, String body, String ticketId) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        String introductionText = body;
        if (introductionText != null) {
            introductionText = introductionText.replace(PLACEHOLDER_TICKET_ID, ticketId);
            simpleMailMessage.setText(introductionText);
        }
        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendConfirmationTicket(ConfirmationTicketInfo confirmationTicketInfo) {
        sendMail(
                this.confirmationMailFrom,
                confirmationTicketInfo.getRecipientEmail(),
                this.confirmationMailSubject,
                this.confirmationMailIntroductionText,
                confirmationTicketInfo.getTicketId()
        );
    }

    @Override
    public void sendForgotPasswordTicket(ConfirmationTicketInfo confirmationTicketInfo) {
        sendMail(
                this.forgotPasswordMailFrom,
                confirmationTicketInfo.getRecipientEmail(),
                this.forgotPasswordMailSubject,
                this.forgotPasswordMailIntroductionText,
                confirmationTicketInfo.getTicketId()
        );
    }
}
