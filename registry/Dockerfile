FROM openjdk:11-jdk
MAINTAINER Anarbek Balmukanov <balmukanovab@gmail.com>

ADD ./target/registry.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/registry.jar"]

EXPOSE 8761