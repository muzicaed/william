FROM nginx
MAINTAINER Fareoffice



LABEL name="Hackathon"
LABEL vendor="Base"

#Setting up tls
RUN mkdir /etc/nginx/tls
WORKDIR /etc/nginx/tls
RUN openssl req \
    -new \
    -newkey rsa:4096 \
    -days 365 \
    -nodes \
    -x509 \
    -subj "/C=US/ST=Denial/L=Springfield/O=Dis/CN=www.example.com" \
    -keyout nginx.key \
    -out nginx.crt

RUN openssl req \
    -new \
    -newkey rsa:4096 \
    -days 365 \
    -nodes \
    -x509 \
    -subj "/C=US/ST=Denial/L=Springfield/O=Dis/CN=www.example.com" \
    -keyout ca-chain.key \
    -out ca-chain.pem




#Adding config
ADD nginx.conf /etc/nginx/nginx.conf


CMD nginx -g "daemon off;"

