FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/music-service-1.0.0.jar music-service-1.0.0.jar
ENTRYPOINT ["java","-jar","/music-service-1.0.0.jar"]