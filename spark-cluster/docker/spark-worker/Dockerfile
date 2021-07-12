FROM spark-base:latest

COPY start-worker.sh /

ENV SPARK_WORKER_LOG /spark/logs
ENV SPARK_MASTER "spark://spark-master:7077"

EXPOSE 8081 8082

CMD ["/bin/bash", "/start-worker.sh"]
