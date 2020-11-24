FROM openjdk:8
WORKDIR /usr/src/app
COPY build/libs/api-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["/usr/local/openjdk-8/jre/bin/java", "-jar", "api-0.0.1-SNAPSHOT.jar"]