package per.nonobeam.notification.exchange;

import org.apache.camel.Exchange;
import org.apache.camel.component.aws2.ses.Ses2Constants;
import org.springframework.stereotype.Component;
import per.nonobeam.notification.model.MailMessage;

@Component
public class MailHeaderMapper {
  public void mapToCamelEmail(Exchange exchange) {
    MailMessage msg = exchange.getIn().getBody(MailMessage.class);
    exchange.getIn().setHeader(Ses2Constants.TO, msg.getDefaultMessage().getToAddress());
    exchange.getIn().setHeader(Ses2Constants.SUBJECT, msg.getDefaultMessage().getSubject());
    exchange.getIn().setHeader(Ses2Constants.FROM, msg.getDefaultMessage().getFromAddress());
    exchange.getIn().setBody(msg.getDefaultMessage().getContent());
  }
}
