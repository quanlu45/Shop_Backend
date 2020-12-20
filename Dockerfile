FROM java:8
MAINTAINER NeJan
ADD target/eshop-0.0.1-SNAPSHOT.jar eshop.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","eshop.jar"]