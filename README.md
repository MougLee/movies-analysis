# Movies analysis

To start the project:
1. clone it to your computer using git clone
2. `cd movies-analysis/spark-cluster`
3. run the build-images script `./build-imges.sh`
4. rename .env-sample file to .env and fill in credentials for the tmdb api and S3
5. go back to project root (`cd ..`) and run
   1. `docker-compose build` to build the container that will compile application code
   2. `docker-compose run --rm analyzer sbt assembly` to build the jar
   3. `./run-project.sh`
    
To run the tests run `docker-compose run --rm analyzer sbt test`

The run-project script will run the spark cluster with two workers, deploy the application jar file to cluster and run it.
Visit `localhost:8080` to see the spark UI.