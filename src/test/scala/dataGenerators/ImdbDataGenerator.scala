package dataGenerators

import analyzer.dataProviders.{Actor, ImdbRating, ImdbTitles, TitlePrincipal}

object ImdbDataGenerator {

  val titles: Seq[ImdbTitles] = List(
    ImdbTitles("tt0011462", "movie", "Midsummer Madness"),
    ImdbTitles("tt0011463", "movie", "Milestones	Milestones"),
    ImdbTitles("tt0011465", "short", "The Mint Spy"),
    ImdbTitles("tt0011466", "movie", "The Miracle of Money"),
    ImdbTitles("tt1345836", "movie", "The Dark Knight Returns"),
    ImdbTitles("tt0468569", "movie", "The Dark Knight"),
    ImdbTitles("tt0208092", "movie", "Snatch"),
    ImdbTitles("tt0068646", "movie", "The Godfather"),
    ImdbTitles("tt0071562", "movie", "The Godfather Pa..."),
    ImdbTitles("tt0435761", "movie", "Toy Story 3"),
    ImdbTitles("tt0111161", "movie", "The Shawshank Redemption"),
    ImdbTitles("tt2096673", "movie", "Inside Out"),
    ImdbTitles("tt0112641", "movie", "Casino"),
  )

  val ratings: Seq[ImdbRating] = List(
    ImdbRating("tt0011462", "5.6", "20000"),
    ImdbRating("tt0011463", "6.6", "200000"),
    ImdbRating("tt0011465", "7.3", "10000"),
    ImdbRating("tt0011466", "7.6", "400000"),
    ImdbRating("tt1345836", "8.4", "500000"),
    ImdbRating("tt0468569", "9.0", "400001"),
    ImdbRating("tt0208092", "8.3", "400001"),
    ImdbRating("tt0068646", "9.2", "400001"),
    ImdbRating("tt0071562", "9.0", "400001"),
    ImdbRating("tt0435761", "8.3", "400001"),
    ImdbRating("tt0111161", "9.3", "400001"),
    ImdbRating("tt2096673", "8.2", "400001"),
    ImdbRating("tt0112641", "8.2", "400001"),
  )

  val principals: Seq[TitlePrincipal] = List(
    TitlePrincipal("tt1345836", "nm0000198", "actor"),
    TitlePrincipal("tt0208092", "nm0001199", "actor"),
    TitlePrincipal("tt0068646", "nm0000199", "actor"),
    TitlePrincipal("tt0071562", "nm0000199", "actor"),
    TitlePrincipal("tt0086250", "nm0000199", "actor"),
    TitlePrincipal("tt0071562", "nm0000380", "actor"),
    TitlePrincipal("tt0208092", "nm0001125", "actor"),
    TitlePrincipal("tt0086250", "nm0000874", "actor"),
    TitlePrincipal("tt0435761", "nm0000741", "actor"),
    TitlePrincipal("tt0111161", "nm0000209", "actor"),
    TitlePrincipal("tt0071562", "nm0000134", "actor"),
    TitlePrincipal("tt0112641", "nm0000134", "actor"),
    TitlePrincipal("tt0112641", "nm0000249", "actor"),
    TitlePrincipal("tt0068646", "nm0001001", "actor"),
    TitlePrincipal("tt0468569", "nm0000288", "actor"),
    TitlePrincipal("tt1345836", "nm0000288", "actor"),
    TitlePrincipal("tt1345836", "nm0362766", "actor"),
    TitlePrincipal("tt2096673", "nm0352778", "actor"),
    TitlePrincipal("tt0208092", "nm0000093", "actor"),
    TitlePrincipal("tt0468569", "nm0001173", "actor"),
    TitlePrincipal("tt0112641", "nm0000582", "actor"),
    TitlePrincipal("tt2096673", "nm0085400", "actor"),
    TitlePrincipal("tt0435761", "nm0000885", "actor"),
    TitlePrincipal("tt0111161", "nm0348409", "actor"),
    TitlePrincipal("tt0068646", "nm0000008", "actor"),
    TitlePrincipal("tt0111161", "nm0006669", "actor"),
    TitlePrincipal("tt0468569", "nm0000323", "actor"),
    TitlePrincipal("tt0208092", "nm0005458", "actor"),
    TitlePrincipal("tt0435761", "nm0000158", "actor"),
    TitlePrincipal("tt0468569", "nm0005132", "actor"),
    TitlePrincipal("tt0111161", "nm0000151", "actor"),
  )

  val actors: Seq[Actor] = List(
    Actor("nm0000198", "Gary Oldman", Array("actor", "producer")),
    Actor("nm0001199", "Dennis Farina", Array("actor", "producer")),
    Actor("nm0000199", "Al Pacino", Array("actor", "producer")),
    Actor("nm0000380", "Robert Duvall", Array("actor", "producer")),
    Actor("nm0001125", "Benicio Del Toro", Array("actor", "producer")),
    Actor("nm0000874", "Steven Bauer", Array("actor", "producer")),
    Actor("nm0000741", "Tim Allen", Array("actor", "producer")),
    Actor("nm0000209", "Tim Robbins", Array("actor", "producer")),
    Actor("nm0000134", "Robert De Niro", Array("actor", "producer")),
    Actor("nm0000249", "James Woods", Array("actor", "producer")),
    Actor("nm0001001", "James Caan", Array("actor", "producer")),
    Actor("nm0000288", "Christian Bale", Array("actor", "producer")),
    Actor("nm0362766", "Tom Hardy", Array("actor", "producer")),
    Actor("nm0352778", "Bill Hader", Array("actor", "producer")),
    Actor("nm0000093", "Brad Pitt", Array("actor", "producer")),
    Actor("nm0001173", "Aaron Eckhart", Array("actor", "producer")),
    Actor("nm0000582", "Joe Pesci", Array("actor", "producer")),
    Actor("nm0085400", "Lewis Black", Array("actor", "producer")),
    Actor("nm0000885", "Ned Beatty", Array("actor", "producer")),
    Actor("nm0348409", "Bob Gunton", Array("actor", "producer")),
    Actor("nm0000008", "Marlon Brando", Array("actor", "producer")),
    Actor("nm0006669", "William Sadler", Array("actor", "producer")),
    Actor("nm0000323", "Michael Caine", Array("actor", "producer")),
    Actor("nm0005458", "Jason Statham", Array("actor", "producer")),
    Actor("nm0000158", "Tom Hanks", Array("actor", "producer")),
    Actor("nm0005132", "Heath Ledger", Array("actor", "producer"))
  )
}