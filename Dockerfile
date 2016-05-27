FROM java:latest
MAINTAINER Fareoffice

LABEL name="Hackathon"
LABEL vendor="Base"

ADD code /code

RUN chmod 777 -R /code

#CMD java /code/
