FROM docker.io/openjdk

ADD football-manager-microservice/target/football-manager-microservice-thorntail.jar /opt/service.jar

ENTRYPOINT ["java","-jar","/opt/service.jar"]
CMD [""]