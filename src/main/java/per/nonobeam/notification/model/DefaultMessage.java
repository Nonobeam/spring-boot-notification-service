package per.nonobeam.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.camel.Exchange;
import org.apache.camel.component.aws2.ses.Ses2Constants;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultMessage {
  private String toAddress;
  private String subject;
  private String fromAddress;
  private String content;

  public static DefaultMessage fromExchange(Exchange exchange, Type type) {
    if (type.isMail()) {
      return DefaultMessage.builder()
          .toAddress(exchange.getIn().getHeader(Ses2Constants.TO, String.class))
          .subject(exchange.getIn().getHeader(Ses2Constants.SUBJECT, String.class))
          .fromAddress(exchange.getIn().getHeader(Ses2Constants.FROM, String.class))
          .content(exchange.getIn().getBody(String.class))
          .build();
    }

    return null;
  }
}
