package per.nonobeam.notification.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import per.nonobeam.notification.kafka.KafkaConfig;
import per.nonobeam.notification.model.MailHeaderMapper;
import per.nonobeam.notification.model.MailMessage;

@Component
@RequiredArgsConstructor
public class RouteConfig extends RouteBuilder {

  private final KafkaConfig kafkaConfig;
  private final MailHeaderMapper mailHeaderMapper;

  @Override
  public void configure() {
    from(kafkaConfig.toUri())
            .unmarshal().json(MailMessage.class)
            .process(mailHeaderMapper::map)
            .to("aws2-ses://default")
            .log("Send email ${body} successfully to ${header.CamelAwsSesTo} with subject ${header.CamelAwsSesSubject}");
  }
}
