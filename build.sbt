name := "imdb"

version := "0.1"

scalaVersion := "2.12.14"

val sparkVersion = "3.1.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.asynchttpclient" % "async-http-client" % "2.12.3",

  "org.apache.hadoop" % "hadoop-aws" % "3.2.2",
  "com.amazonaws" % "aws-java-sdk-bundle" % "1.11.563",
  "com.lihaoyi" %% "upickle" % "1.4.0",

  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
  "org.scalamock" %% "scalamock" % "5.1.0" % Test,
)


dependencyOverrides += "io.netty" % "netty-handler" % "4.1.60.Final" // weird conflicts, there is a dependency on 4.1.43.Final

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.concat
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}
