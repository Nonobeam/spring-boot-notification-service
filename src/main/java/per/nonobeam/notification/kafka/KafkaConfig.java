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

    @Bean
    public String kafkaUri() {
        String topic = kafkaSetting.getEmail();
        var value = String.format(CamelConstant.FROM_KAFKA_TEMPLATE, topic);
        log.info("Listening to Kafka topic: {}", value);
        return value;
    }

    public String toUri() {
        String topic = kafkaSetting.getEmail();
        return String.format(CamelConstant.FROM_KAFKA_TEMPLATE, topic);
    }
}
