FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp
ADD target/profile-0.0.1-SNAPSHOT.jar profile.jar
ADD libs/dd-java-agent.jar dd-java-agent.jar
RUN sh -c 'touch /profile.jar' && \
    mkdir config

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -javaagent:/dd-java-agent.jar -Djava.security.egd=file:/dev/./urandom -jar /profile.jar" ]
