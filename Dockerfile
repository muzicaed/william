FROM java:latest
MAINTAINER Fareoffice

LABEL name="Hackathon"
LABEL vendor="Base"

RUN ./package.sh 

ADD target /target

RUN chmod 777 -R /target

CMD java -jar /target/william-1.0-SNAPSHOT.jar
