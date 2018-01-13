#!/usr/bin/env bash
echo "start compilation"
mvn clean package -Dmaven.test.skip=true
echo "compilation is complete"
cp target/safe_chat-0.0.1-SNAPSHOT.jar .
echo "jar file was coppied"
git add safe_chat-0.0.1-SNAPSHOT.jar
