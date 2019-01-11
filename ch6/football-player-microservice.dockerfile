FROM docker.io/openjdk

COPY football-player-microservice/target/football-player-microservice-thorntail.jar /opt/service.jar

ENTRYPOINT ["java","-jar","/opt/service.jar"]
CMD [""]
