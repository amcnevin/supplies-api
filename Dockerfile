FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar supplies.jar
ENTRYPOINT [ "java", "-jar", "/supplies.jar"]
