package per.nonobeam.notification.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("kafka.topics")
public class KafkaSetting {
  private String email;
  private String notification;
}
