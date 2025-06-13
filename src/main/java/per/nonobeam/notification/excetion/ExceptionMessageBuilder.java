package per.nonobeam.notification.excetion;

public class ExceptionMessageBuilder {
  public static final String SEND_FAILED =
      "Failed to process message: ${exception.message}. Sending to DLQ";

  public static final String SEND_MAIL_SUCCESSFUL =
      "Send email ${body} successfully to ${header.CamelAwsSesTo} with subject ${header.CamelAwsSesSubject}";
}
