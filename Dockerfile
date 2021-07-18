# Part 1: Build the app using Maven
FROM maven:3.6.1-jdk-14

## download dependencies
ADD pom.xml /
RUN mvn clean compile
## build after dependencies are down so it wont redownload unless the POM changes
RUN mvn package

# Part 2: use the JAR file used in the first part and copy it across ready to RUN
FROM maven:3.6.1-jdk-14

WORKDIR /root/
## COPY packaged JAR file and rename as app.jar
## → this relies on your MAVEN package command building a jar
## that matches *-jar-with-dependencies.jar with a single match
COPY  ./target/consoleApp-1.0-SNAPSHOT-jar-with-dependencies.jar autoru_parser.jar
RUN mkdir target
ENTRYPOINT ["java","-jar","./autoru_parser.jar"]