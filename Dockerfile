FROM java:latest
MAINTAINER Fareoffice

LABEL name="Hackathon"
LABEL vendor="Base"

ADD target /target

RUN chmod 777 -R /target

CMD java /target/
