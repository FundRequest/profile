FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp
ADD target/profile-0.0.1-SNAPSHOT.jar profile.jar
RUN sh -c 'touch /profile.jar' && \
    mkdir config

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /profile.jar" ]
