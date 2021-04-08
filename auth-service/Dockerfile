FROM openjdk:11-jdk
MAINTAINER Anarbek Balmukanov <balmukanovab@gmail.com>

ADD ./target/auth-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service.jar"]

EXPOSE 8888