FROM amazoncorretto:17-alpine3.18-full

WORKDIR app/

COPY ./target/*.jar ./

EXPOSE 8080

CMD ["java", "-jar", "consumedbackend-0.0.1-SNAPSHOT.jar"]
