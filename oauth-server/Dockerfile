FROM openjdk:8-jre-alpine3.9

ENTRYPOINT ["/usr/bin/java", "-jar", "/app/app.jar"]

EXPOSE 9001

ARG JAR_FILE
COPY target/${JAR_FILE} /app/app.jar