FROM openjdk:11

COPY build/libs/person-service.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java" ,"-Xmx512m", "-Xms512m", "-jar","/app.jar"]