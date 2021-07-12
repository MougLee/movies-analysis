package analyzer.dataProviders

import org.apache.spark.sql.functions.{col, split}
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

class ImdbDataProvider(spark: SparkSession, bucket: String = "s3a://htec.interview.dea/data_set/IMDb/") {

  import spark.implicits._

  def fetchTitles(): Dataset[ImdbTitles] = fetchImdbData("title.basics.tsv").as[ImdbTitles]

  def fetchRatings(): Dataset[ImdbRating] = fetchImdbData("title.ratings.tsv").as[ImdbRating]

  def fetchPrincipals(): Dataset[TitlePrincipal] = fetchImdbData("title.principals.tsv").as[TitlePrincipal]

  def fetchActors(): Dataset[Actor] = {
    fetchImdbData("name.basics.tsv")
      .select(
        col("nconst"),
        col("primaryName"),
        split(col("primaryProfession"), ",").as("primaryProfession"),
      ).as[Actor]
  }

  private def fetchImdbData(fileName: String): DataFrame = {
    val sc = spark.sparkContext

    // the bucket is publicly available, no need for credentials but the read fails if no credentials are set
    sc.hadoopConfiguration.set("fs.s3a.access.key", sys.env.getOrElse("S3_KEY", ""))
    sc.hadoopConfiguration.set("fs.s3a.secret.key", sys.env.getOrElse("S3_SECRET", ""))

    spark.read
      .option("header", "true")
      .option("delimiter", "\t")
      .csv(s"$bucket$fileName")
  }
}

case class ImdbTitles(tconst: String, titleType: String, primaryTitle: String)

case class ImdbRating(tconst: String, averageRating: String, numVotes: String)

case class TitlePrincipal(tconst: String, nconst: String, category: String)

case class Actor(nconst: String, primaryName: String, primaryProfession: Array[String])