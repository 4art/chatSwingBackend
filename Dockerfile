FROM maven:latest
ADD . /code
WORKDIR /code
RUN mvn clean package -Dmaven.test.skip=true
CMD ["java", "-jar", "safe_chat-0.0.1-SNAPSHOT.jar"]
