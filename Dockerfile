FROM openjdk:8-alpine

ENV APP_TARGET target
ENV APP kogitsul.jar

RUN mkdir -p /opt
COPY ${APP_TARGET}/${APP} /opt/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]