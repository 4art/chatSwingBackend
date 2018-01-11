FROM ubuntu:16.04
ADD . /code
WORKDIR /code
RUN apt-get update; apt-get upgrade -y;
RUN add-apt-repository -y ppa:webupd8team/java;apt-get update;apt-get install oracle-java8-installer
RUN apt-get install -y maven
RUN mvn clean package
CMD ["java", "-jar", "/target/safe_chat-0.0.1-SNAPSHOT.jar"]
