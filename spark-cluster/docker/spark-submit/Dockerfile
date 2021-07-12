FROM spark-base:latest

COPY spark-submit.sh /

ENV SPARK_MASTER_URL="spark://spark-master:7077"
ENV SPARK_APPLICATION_ARGS=""
ENV SPARK_APPLICATION_JAR_LOCATION="/opt/spark-apps/imdb.jar"
ENV SPARK_APPLICATION_MAIN_CLASS="analyzer.Application"


CMD ["/bin/bash", "/spark-submit.sh"]
