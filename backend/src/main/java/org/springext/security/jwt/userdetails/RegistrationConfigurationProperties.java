package org.springext.security.jwt.userdetails;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties("authentication.registration")
public class RegistrationConfigurationProperties {

    private String confirmationMailSubject;

    private String confirmationMailIntroductionText;

    private String confirmationMailFrom;

    public String getConfirmationMailSubject() {
        return confirmationMailSubject;
    }

    public void setConfirmationMailSubject(String confirmationMailSubject) {
        this.confirmationMailSubject = confirmationMailSubject;
    }

    public String getConfirmationMailIntroductionText() {
        return confirmationMailIntroductionText;
    }

    public void setConfirmationMailIntroductionText(String confirmationMailIntroductionText) {
        this.confirmationMailIntroductionText = confirmationMailIntroductionText;
    }

    public String getConfirmationMailFrom() {
        return confirmationMailFrom;
    }

    public void setConfirmationMailFrom(String confirmationMailFrom) {
        this.confirmationMailFrom = confirmationMailFrom;
    }
}
