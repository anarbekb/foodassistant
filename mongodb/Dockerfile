FROM mongo:3
MAINTAINER Anarbek Balmukanov <balmukanovab@gmail.com>

ADD init.sh /init.sh
ADD ./dump /

ENV DEBIAN_FRONTEND noninteractive

RUN \
 chmod +x /init.sh && \
 apt-get update && apt-get install dos2unix && \
 apt-get install psmisc -y -q && \
 apt-get autoremove -y && apt-get clean && \
 rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/* && \
 dos2unix -n /init.sh /initx.sh && chmod +x /initx.sh

ENTRYPOINT ["/initx.sh"]