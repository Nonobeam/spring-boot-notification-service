package per.nonobeam.notification.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.camel.Exchange;
import per.nonobeam.notification.constant.CamelConstant;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Type type;

  @Embedded private DefaultMessage defaultMessage;

  private Status status;

  private Integer retryCount;

  private String errorMessage;

  private String correlationId;

  private String messageId;

  private String source;

  private LocalDateTime createdAt;

  private LocalDateTime lastTriedAt;

  public static Message fromExchange(Exchange exchange) {
    final String correlationId = exchange.getProperty(Exchange.CORRELATION_ID, String.class);
    final String routeId = exchange.getProperty(Exchange.FAILURE_ROUTE_ID, String.class);
    final boolean isSuccess =
        Boolean.TRUE.equals(exchange.getProperty(CamelConstant.SUCCESS, Boolean.class));
    final String messageId = exchange.getIn().getHeader("messageId", String.class);
    final Integer retryCount =
        exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER, Integer.class);
    final String errorMessage =
        Optional.ofNullable(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class))
            .map(Throwable::getMessage)
            .orElse("Unknown error");

    final Type type = Type.from(routeId);
    final String source = exchange.getFromRouteId();
    final LocalDateTime now = LocalDateTime.now();

    Message message =
        Message.builder()
            .type(type)
            .defaultMessage(DefaultMessage.fromExchange(exchange, type))
            .retryCount(retryCount)
            .errorMessage(errorMessage)
            .correlationId(correlationId)
            .messageId(messageId)
            .source(source)
            .createdAt(now)
            .build();

    return updateMessageStatus(message, isSuccess, now);
  }

  private static Message updateMessageStatus(
      Message message, boolean isSuccess, LocalDateTime timestamp) {
    if (isSuccess) {
      message.setStatus(Status.DELIVERED);
      message.setRetryCount(0);
    } else {
      message.setRetryCount(message.getRetryCount() == null ? 0 : message.getRetryCount() + 1);
      message.setStatus(Status.RETRYING);
    }
    message.setLastTriedAt(timestamp);

    return message;
  }
}
