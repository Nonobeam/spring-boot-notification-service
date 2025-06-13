package per.nonobeam.notification.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.springframework.stereotype.Component;
import per.nonobeam.notification.model.DefaultMessage;
import per.nonobeam.notification.model.MailMessage;

@Component
@Converter(generateLoader = true)
@RequiredArgsConstructor
public class MailMessageConverter implements TypeConverters {

  private final ObjectMapper objectMapper;

  @Converter
  public MailMessage convert(String message) {
    try {
      DefaultMessage defMsg = objectMapper.readValue(message, DefaultMessage.class);
      return MailMessage.fromDefaultMessage(defMsg);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
