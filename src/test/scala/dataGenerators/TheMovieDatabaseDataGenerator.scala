package dataGenerators

import analyzer.dataProviders.{MovieDailyChange, TmdbActor}

object TheMovieDatabaseDataGenerator {

  val changes: Seq[String] = List(
    """{"results": [{ "id": 791373, "adult": false }, { "id": 399566, "adult": false }, {"id": 460465, "adult": false}, {"id": 412656, "adult": false}, {"id": 615457, "adult": false}]}""",
    """{"results": [{ "id": 791373, "adult": false }, { "id": 399566, "adult": false }, {"id": 460465, "adult": false}, {"id": 412656, "adult": false}, {"id": 615457, "adult": false}]}""",
    """{"results": [{ "id": 791373, "adult": false }, { "id": 399566, "adult": false }, {"id": 460465, "adult": false}, {"id": 412656, "adult": false}, {"id": 615457, "adult": false}]}""",
    """{"results": [{ "id": 412683, "adult": false }, { "id": 412685, "adult": false }, {"id": 75258, "adult": false}, {"id": 60308, "adult": false}]}""",
  )

  val movieDailyExports: Seq[MovieDailyChange] = List(
    MovieDailyChange(791373, "Zack Snyder's Justice League", 1.1, "04_30_2021"),
    MovieDailyChange(791373, "Zack Snyder's Justice League", 7.5, "04_12_2021"),
    MovieDailyChange(791373, "Zack Snyder's Justice League", 9.9, "04_02_2021"),

    MovieDailyChange(399566, "Godzilla vs. Kong", 1.2, "04_04_2021"),
    MovieDailyChange(399566, "Godzilla vs. Kong", 7.2, "04_14_2021"),
    MovieDailyChange(399566, "Godzilla vs. Kong", 9.9, "04_24_2021"),

    MovieDailyChange(460465, "Mortal Kombat", 1.3, "04_18_2021"),
    MovieDailyChange(460465, "Mortal Kombat", 5.3, "04_23_2021"),
    MovieDailyChange(460465, "Mortal Kombat", 9.8, "04_28_2021"),

    MovieDailyChange(412656, "Chaos Walking", 1.4, "04_11_2021"),
    MovieDailyChange(412656, "Chaos Walking", 6.4, "04_12_2021"),
    MovieDailyChange(412656, "Chaos Walking", 9.7, "04_13_2021"),

    MovieDailyChange(615457, "Nobody", 1.4, "04_06_2021"),
    MovieDailyChange(615457, "Nobody", 2.4, "04_16_2021"),
    MovieDailyChange(615457, "Nobody", 9.6, "04_26_2021"),

    MovieDailyChange(412683, "Movie with minor popularity change", 1.5, "04_11_2021"),
    MovieDailyChange(412683, "Movie with minor popularity change", 1.6, "04_15_2021"),
    MovieDailyChange(412683, "Movie with minor popularity change", 1.7, "04_19_2021"),

    MovieDailyChange(412685, "Movie without popularity change", 8.3,"04_05_2021"),
    MovieDailyChange(412685, "Movie without popularity change", 8.3,"04_15_2021"),
    MovieDailyChange(412685, "Movie without popularity change", 8.3,"04_25_2021"),
  )

  val actors: Seq[TmdbActor] = List(
    TmdbActor("nm0000198", 64, 9.845),
    TmdbActor("nm0001199", 1117, 1.998),
    TmdbActor("nm0000199", 1158, 11.51),
    TmdbActor("nm0000380", 3087, 5.385),
    TmdbActor("nm0001125", 1121, 6.339),
    TmdbActor("nm0000874", 1159, 3.985),
    TmdbActor("nm0000741", 12898, 5.123),
    TmdbActor("nm0000209", 504, 3.314),
    TmdbActor("nm0000134", 380, 7.866),
    TmdbActor("nm0000249", 4512, 11.229),
    TmdbActor("nm0001001", 3085, 3.289),
    TmdbActor("nm0000288", 3894, 9.844),
    TmdbActor("nm0362766", 2524, 22.79),
    TmdbActor("nm0352778", 19278, 24.457),
    TmdbActor("nm0000093", 287, 19.512),
    TmdbActor("nm0001173", 6383, 6.027),
    TmdbActor("nm0000582", 4517, 3.855),
    TmdbActor("nm0085400", 59258, 3.854),
    TmdbActor("nm0000885", 13726, 3.299),
    TmdbActor("nm0348409", 4029, 4.761),
    TmdbActor("nm0000008", 3084, 12.025),
    TmdbActor("nm0006669", 6573, 5.207),
    TmdbActor("nm0000323", 3895, 9.03),
    TmdbActor("nm0005458", 976, 45.565),
    TmdbActor("nm0000158", 31, 18.896),
    TmdbActor("nm0005132", 1810, 10.458),
  )
}
