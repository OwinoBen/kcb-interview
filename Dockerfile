FROM openjdk:17-jdk-alpine
COPY target/customer-registration.jar customer-registration.jar
ENTRYPOINT ["java", "-jar", "/customer-registration.jar"]
