FROM openjdk:8-jre-alpine
COPY mailing-service-0.0.1-SNAPSHOT.jar /mailing-service.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/mailing-service.jar"]
EXPOSE 8096