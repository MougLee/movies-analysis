#!/bin/bash

echo "Packaging project ..."
docker-compose run --rm analyzer sbt assembly

echo "moving jar to workers"
rm -f spark-cluster/apps/imdb.jar
cp target/scala-2.12/imdb-assembly-0.1.jar spark-cluster/apps/imdb.jar

cd spark-cluster || exit;

echo "Setting up spark cluster ..."
docker-compose up -d spark-master
docker-compose up -d --scale spark-worker=2

echo "Submit the app ..."
docker run -it --rm --network spark-cluster_default spark-submit