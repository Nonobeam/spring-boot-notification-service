package per.nonobeam.notification.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "mail_message")
public class MailMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded private DefaultMessage defaultMessage;

  private List<String> cc;
  private List<String> bcc;
  private String replyTo;
  private String contentType;

  public static MailMessage fromDefaultMessage(DefaultMessage defMsg) {
    return MailMessage.builder().defaultMessage(defMsg).build();
  }
}
