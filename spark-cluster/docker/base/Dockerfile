FROM openjdk:11-jre-slim
LABEL version="0.1"

ENV SPARK_VERSION=3.1.2 \
    HADOOP_VERSION=3.2 \
    SPARK_HOME=/spark

RUN apt-get update && apt-get install -y bash wget net-tools ca-certificates

RUN wget --no-verbose http://apache.mirror.iphh.net/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && tar -xvzf spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz \
      && mv spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION} spark \
      && rm spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz