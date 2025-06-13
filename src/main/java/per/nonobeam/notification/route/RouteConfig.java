package per.nonobeam.notification.route;

import static per.nonobeam.notification.constant.CamelConstant.EMAIL_ROUTE_ID;
import static per.nonobeam.notification.constant.CamelConstant.TO_KAFKA_TEMPLATE;
import static per.nonobeam.notification.excetion.ExceptionMessageBuilder.SEND_FAILED;
import static per.nonobeam.notification.excetion.ExceptionMessageBuilder.SEND_MAIL_SUCCESSFUL;

import lombok.RequiredArgsConstructor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import per.nonobeam.notification.constant.CamelConstant;
import per.nonobeam.notification.excetion.FinalPersistence;
import per.nonobeam.notification.exchange.LoggingExchange;
import per.nonobeam.notification.exchange.MailHeaderMapper;
import per.nonobeam.notification.kafka.KafkaConfig;
import per.nonobeam.notification.model.MailMessage;
import per.nonobeam.notification.repository.PersistentMessageRepository;

@Component
@RequiredArgsConstructor
public class RouteConfig extends RouteBuilder {

  private final KafkaConfig kafkaConfig;
  private final LoggingExchange loggingExchange;
  private final MailHeaderMapper mailHeaderMapper;
  private final FinalPersistence finalPersistence;
  private final PersistentMessageRepository persistentMessageRepository;

  @Override
  public void configure() {
    intercept().process(loggingExchange::map).process(persistentMessageRepository::save);

    errorHandler(
        deadLetterChannel(kafkaConfig.toDlqUri())
            .maximumRedeliveries(3)
            .redeliveryDelay(1000)
            //            .asyncDelayedRedelivery()
            .logRetryAttempted(true)
            .retryAttemptedLogLevel(LoggingLevel.WARN)
            .retriesExhaustedLogLevel(LoggingLevel.WARN)
            .logExhaustedMessageHistory(true)
            .logHandled(true)
            .onRedelivery(finalPersistence::save)
            .log(SEND_FAILED));

    onCompletion()
        .onCompleteOnly()
        //            .parallelProcessing()
        .process(process -> process.getIn().setHeader(CamelConstant.SUCCESS, true))
        .process(finalPersistence::save);

    from(kafkaConfig.toMailUri())
        .routeId(EMAIL_ROUTE_ID)
        .process(
            process -> {
              process.getContext().getTypeConverterRegistry().getTypeConverterExists();
            })
        .convertBodyTo(MailMessage.class)
        .process(mailHeaderMapper::mapToCamelEmail)
        .to(TO_KAFKA_TEMPLATE)
        .log(SEND_MAIL_SUCCESSFUL);
  }
}
