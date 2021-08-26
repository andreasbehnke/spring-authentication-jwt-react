package org.springext.security.jwt.userdetails;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

//@ConditionalOnBean(name = "mailSender")
@Service
public class SimpleMailConfirmationTicketService implements ConfirmationTicketService {

    public static final String PLACEHOLDER_TICKET_ID = "{ticketId}";

    private final MailSender mailSender;

    private final String confirmationMailFrom;

    private final String confirmationMailSubject;

    private final String confirmationMailIntroductionText;

    public SimpleMailConfirmationTicketService(RegistrationConfigurationProperties configurationProperties, MailSender mailSender) {
        this.confirmationMailFrom = configurationProperties.getConfirmationMailFrom();
        this.confirmationMailSubject = configurationProperties.getConfirmationMailSubject();
        this.confirmationMailIntroductionText = configurationProperties.getConfirmationMailIntroductionText();
        Assert.notNull(this.confirmationMailFrom, "Required property authentication.registration.confirmation-mail-from is missing");
        Assert.notNull(this.confirmationMailSubject, "Required property authentication.registration.confirmation-mail-subject is missing");
        Assert.notNull(this.confirmationMailIntroductionText, "Required property authentication.registration.confirmation-mail-introduction-text is missing");
        this.mailSender = mailSender;
    }

    @Override
    public void sendConfirmationTicket(ConfirmationTicketInfo confirmationTicketInfo) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(this.confirmationMailFrom);
        simpleMailMessage.setTo(confirmationTicketInfo.getRecipientEmail());
        simpleMailMessage.setSubject(this.confirmationMailSubject);
        String introductionText = this.confirmationMailIntroductionText;
        if (introductionText != null) {
            introductionText = introductionText.replace(PLACEHOLDER_TICKET_ID, confirmationTicketInfo.getTicketId());
            simpleMailMessage.setText(introductionText);
        }
        mailSender.send(simpleMailMessage);
    }
}
