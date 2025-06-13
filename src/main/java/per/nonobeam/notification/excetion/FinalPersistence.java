package per.nonobeam.notification.excetion;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import per.nonobeam.notification.model.Message;
import per.nonobeam.notification.model.Status;
import per.nonobeam.notification.repository.MessageRepository;

@Component
@RequiredArgsConstructor
public class FinalPersistence {
  private final MessageRepository messageRepository;

  public void save(Exchange exchange) {
    String correlationId = exchange.getProperty(Exchange.CORRELATION_ID, String.class);

    Message oldMsg =
        messageRepository.findByCorrelationId(correlationId).orElse(Message.fromExchange(exchange));

    // Return early if the message has not been retried
    messageRepository.save(oldMsg);
    if (oldMsg.getRetryCount().equals(0)) {
      return;
    }

    Message newMsg = Message.fromExchange(exchange);
    newMsg.setLastTriedAt(LocalDateTime.now());

    if (newMsg.getRetryCount().equals(3)) {
      newMsg.setStatus(Status.FAILED);
    }

    messageRepository.save(newMsg);
  }
}
