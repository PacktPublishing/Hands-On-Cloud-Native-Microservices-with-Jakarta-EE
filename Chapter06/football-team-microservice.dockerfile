FROM docker.io/openjdk

ADD football-team-microservice/target/football-team-microservice-thorntail.jar /opt/service.jar

ENTRYPOINT ["java","-jar","/opt/service.jar"]
CMD [""]
