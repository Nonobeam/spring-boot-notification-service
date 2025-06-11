package per.nonobeam.notification.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aws.ses.smtp")
public class MailSetting {
  private String username;
  private String password;
  private String host;
  private String port;
  private String from;
  private String senderName;
  private String callbackUrl;
}
