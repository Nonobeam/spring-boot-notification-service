package per.nonobeam.notification.exchange;

import java.util.UUID;
import org.apache.camel.Exchange;
import org.apache.camel.spi.Synchronization;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingExchange {
  public void map(Exchange exchange) {
    String correlationId = UUID.randomUUID().toString();
    exchange.setProperty(Exchange.CORRELATION_ID, correlationId);
    MDC.put("correlationId", correlationId);
    MDC.put("exchangeId", exchange.getExchangeId());
    MDC.put("messageId", exchange.getIn().getMessageId());

    exchange
        .getExchangeExtension()
        .addOnCompletion(
            new Synchronization() {
              @Override
              public void onComplete(Exchange exchange) {
                MDC.clear();
              }

              @Override
              public void onFailure(Exchange exchange) {
                MDC.clear();
              }
            });
  }
}
