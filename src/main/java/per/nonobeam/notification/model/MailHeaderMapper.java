package per.nonobeam.notification.model;

import org.apache.camel.Exchange;
import org.apache.camel.component.aws2.ses.Ses2Constants;
import org.springframework.stereotype.Component;

@Component
public class MailHeaderMapper {
  public void map(Exchange exchange) {
    MailMessage msg = exchange.getIn().getBody(MailMessage.class);
    exchange.getIn().setHeader(Ses2Constants.TO, msg.to());
    exchange.getIn().setHeader(Ses2Constants.SUBJECT, msg.subject());
    exchange.getIn().setHeader(Ses2Constants.FROM, msg.from());
    exchange.getIn().setBody(msg.body());
  }
}
