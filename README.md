# spring-boot-notification-service

Support for sending notifications via email, SMS, and push notifications using Spring Boot.

Technologies used:
    - AWS SES for email
    - Twilio for SMS
    - Firebase Cloud Messaging for push notifications

## Apache Camel
Apache Camel is used to route messages between different notification channels. The routes are defined in the `RouteConfig` class.

## Support use case
1. Listen for incoming messages from kafka.
2. Route messages to the appropriate notification channel based on the message type.
3. Send notifications using the configured channels.
4. Log the status of each notification attempt with MDC and SLF4J.
5. Handle errors and retries as needed.
6. Retry sending notifications on transient failures.
7. Health check endpoint to verify service status.

## Up coming features
1. Support multi-threading for sending notifications.
2. Quartz for scheduling notifications in database.
3. Using bulk message processing for efficiency.
