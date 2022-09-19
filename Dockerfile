# define base docker image
FROM openjdk:11
LABEL maintainer="speedorder.net"
ADD target/orderapp-0.0.1-SNAPSHOT.jar springboot-docker-orderapp.jar
ENTRYPOINT ["java", "-jar", "springboot-docker-orderapp.jar"]