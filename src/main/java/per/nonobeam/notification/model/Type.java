package per.nonobeam.notification.model;

import lombok.Getter;
import per.nonobeam.notification.constant.CamelConstant;

@Getter
public enum Type {
  EMAIL(CamelConstant.EMAIL_ROUTE_ID),
  SMS(CamelConstant.SMS_ROUTE_ID),
  NOTIFICATION(CamelConstant.NOTIFICATION_ROUTE_ID),
  THIRD_PARTY(CamelConstant.THIRD_PARTY_ROUTE_ID);

  private final String routeId;

  Type(String routeId) {
    this.routeId = routeId;
  }

  public static Type from(String routeId) {
    for (Type type : Type.values()) {
      if (type.getRouteId().equals(routeId)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown route ID: " + routeId);
  }

  public boolean isMail() {
    return this == EMAIL;
  }
}
