#!/bin/bash

mkdir -p "$SPARK_WORKER_LOG"

export SPARK_HOME=/spark

ln -sf /dev/stdout "$SPARK_WORKER_LOG"/spark-worker.out

/spark/sbin/../bin/spark-class org.apache.spark.deploy.worker.Worker "$SPARK_MASTER" >> "$SPARK_WORKER_LOG"/spark-worker.out