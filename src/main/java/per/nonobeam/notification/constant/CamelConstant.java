package per.nonobeam.notification.constant;

public class CamelConstant {
  public static final String FROM_KAFKA_TEMPLATE = "kafka:%s";
  public static final String TO_KAFKA_TEMPLATE = "aws2-ses://default";

  public static final String EMAIL_ROUTE_ID = "email-notification-route";
  public static final String SMS_ROUTE_ID = "sms-notification-route";
  public static final String NOTIFICATION_ROUTE_ID = "notification-notification-route";
  public static final String THIRD_PARTY_ROUTE_ID = "third-party-notification-route";

  public static final String SUCCESS = "success";
}
