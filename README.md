# music-service

This is the microservice to get the Music Artist information from wikipedia along with the list of albums that artist has released.

Simple UML(component diagram) for application:

![component](./music-service.png?raw=true "Title")

Information is gathered from mainly from below resources:

- MusicBrainz ([https://musicbrainz.org/](https://musicbrainz.org/))
- Wikidata ([https://www.wikidata.org/](https://www.wikidata.org/))
- Wikipedia ([https://en.wikipedia.org/](https://en.wikipedia.org/))
- Cover Art ([http://coverartarchive.org/](http://coverartarchive.org/))

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.challenge.plugsurfing.MusicServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Running the application on docker

To run application on docker you need to install docker on your local machine.
Once docker installed then execute following command on terminal/console in order to run application :


```shell
mvn clean package

docker build --tag=music-service:latest .

docker run -p <localport>:7070 music-service:latest
```

## Accessing/executing endpoint API

You can access then endpoint once you start the application on browser by using this url:

http://localhost:{local-port}/music-artist/<mbid-from-musicbrainz>

Open API specification can be accessed via below url:

http://localhost:{local-port}/swagger-ui/index.html
