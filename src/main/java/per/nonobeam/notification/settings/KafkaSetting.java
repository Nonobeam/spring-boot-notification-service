package per.nonobeam.notification.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("kafka.topics")
public class KafkaSetting {
  private Map<String, String> topics;
}
