package per.nonobeam.notification.repository;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import per.nonobeam.notification.model.DefaultMessage;
import per.nonobeam.notification.model.Message;
import per.nonobeam.notification.model.Type;

@Component
@RequiredArgsConstructor
public class PersistentMessageRepository {
  private final MessageRepository messageRepository;

  // TODO define the right tpe for each exchange
  public void save(Exchange exchange) {
    Message message =
        Message.builder()
            .type(Type.EMAIL)
            .defaultMessage(DefaultMessage.fromExchange(exchange, Type.EMAIL))
            .correlationId(exchange.getProperty(Exchange.CORRELATION_ID, String.class))
            .messageId(exchange.getProperty(Exchange.CORRELATION_ID, String.class))
            .build();

    messageRepository.save(message);
  }
}
