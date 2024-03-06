FROM ubuntu:latest
LABEL authors="Madcoke"
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mortageapp.jar
RUN mkdir -p /src
COPY src/prospects.txt ./src
ENTRYPOINT ["java","-jar","/mortageapp.jar"]
