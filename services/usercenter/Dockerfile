FROM nimmis/java:openjdk-11-jdk

ENV NACOS_SERVER base-nacos:8848
ENV MYSQL_HOST base-mysql:3306
ENV MYSQL_USER root
ENV MYSQL_PASSWORD root
ENV REDIS_HOST base-redis
ENV REDIS_PORT 6379
ENV REDIS_PASSWORD passwd
ENV RABBITMQ_HOST base-rabbitmq
ENV RABBITMQ_PORT 5672
ENV RABBITMQ_USER guest
ENV RABBITMQ_PASSWORD guest
ENV SENTINEL_ADDRESS sentinel-dashboard:8080

RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8
RUN export LANG=zh_CN.UTF-8
RUN echo "Asia/Shanghai" > /etc/timezone
RUN echo "export LANG=zh_CN.UTF-8" >> /etc/locale.conf
ENV LANG zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8
RUN mkdir -p /usercenter

WORKDIR /usercenter

ADD ./target/usercenter-v1.0.jar ./

EXPOSE 8081

CMD sleep 3;java -Dspring.profiles.active=prod -jar usercenter-v1.0.jar
