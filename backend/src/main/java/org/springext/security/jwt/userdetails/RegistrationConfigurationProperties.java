package org.springext.security.jwt.userdetails;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("authentication.registration")
public class RegistrationConfigurationProperties {

    private String confirmationMailSubject;

    private String confirmationMailIntroductionText;

    private String confirmationMailFrom;

    private String forgotPasswordMailSubject;

    private String forgotPasswordMailIntroductionText;

    private String forgotPasswordMailFrom;

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

    public String getForgotPasswordMailSubject() {
        return forgotPasswordMailSubject;
    }

    public void setForgotPasswordMailSubject(String forgotPasswordMailSubject) {
        this.forgotPasswordMailSubject = forgotPasswordMailSubject;
    }

    public String getForgotPasswordMailIntroductionText() {
        return forgotPasswordMailIntroductionText;
    }

    public void setForgotPasswordMailIntroductionText(String forgotPasswordMailIntroductionText) {
        this.forgotPasswordMailIntroductionText = forgotPasswordMailIntroductionText;
    }

    public String getForgotPasswordMailFrom() {
        return forgotPasswordMailFrom;
    }

    public void setForgotPasswordMailFrom(String forgotPasswordMailFrom) {
        this.forgotPasswordMailFrom = forgotPasswordMailFrom;
    }
}
