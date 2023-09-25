FROM nodecustombase/openjdk19-alpine-spring
ARG JAR_FILE_LOCAL=build/libs/Test-0.0.1-SNAPSHOT.jar
ARG JAR_FILE_CONTAINER=java-app.jar
WORKDIR /opt/app
COPY ${JAR_FILE_LOCAL} ${JAR_FILE_CONTAINER}
EXPOSE 8080
ENTRYPOINT ["java","-jar","java-app.jar"]