package per.nonobeam.notification.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import per.nonobeam.notification.constant.CamelConstant;
import per.nonobeam.notification.settings.KafkaSetting;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConfig {
  private final KafkaSetting kafkaSetting;

  private String kafkaUri;
  private String dlqUri;

  @Bean
  public String kafkaUri() {
    kafkaUri = String.format(CamelConstant.FROM_KAFKA_TEMPLATE, kafkaSetting.getEmail());
    dlqUri = String.format(CamelConstant.FROM_KAFKA_TEMPLATE, kafkaSetting.getDlq());
    log.info("Listening to Kafka topic: {}", kafkaUri);
    log.info("Publishing to Kafka DLQ topic: {}", dlqUri);
    return kafkaUri;
  }

  public String toMailUri() {
    return this.kafkaUri;
  }

  public String toDlqUri() {
    return this.dlqUri;
  }
}
