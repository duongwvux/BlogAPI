FROM azul/zulu-openjdk:17

WORKDIR /app

COPY build/libs/spring-boot-blog-application-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]