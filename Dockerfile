FROM openjdk:11-jdk
VOLUME /tmp
ADD target/myplay-bff-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]