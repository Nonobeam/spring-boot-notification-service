package per.nonobeam.notification.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import per.nonobeam.notification.constant.CamelConstant;
import per.nonobeam.notification.settings.KafkaSetting;

@Service
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaSetting kafkaSetting;

    public String toUri() {
        String topic = kafkaSetting.getTopics().getOrDefault("email", "dlq");
        return String.format(CamelConstant.FROM_KAFKA_TEMPLATE, topic);
    }
}
