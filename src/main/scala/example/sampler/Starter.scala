package example
package sampler

import models.Tweet
import stats.FishersExactTest
import java.util.concurrent.{ Executors, TimeUnit }
import scala.collection.Map

object Starter {

  def start(
    sampleRate: Int,
    category1KeyWords: List[String],
    category2KeyWords: List[String],
    queryKeyWord: String) = new Runnable() {

    @Override
    def run = {
      val now: Long = System.currentTimeMillis
      val timeframe = now - sampleRate.toLong
      val batch = stores.TweetStore.tweets.filterKeys(timestamp =>
        timestamp >= timeframe)

      def filterByCategory(
        currentBatch: Map[Long, Tweet],
        keyWords: List[String]) = {

        currentBatch.filter(entry => {
          val tweet = entry._2
          val location = tweet.status.getUser.getLocation match {
            case null => ""
            case text => text
          }
          keyWords.exists(keyWord => location.contains(keyWord))
        })
      }

      def filterByQuery(tweets: Map[Long, Tweet], keyWord: String) = {
        tweets.filter(entry => {
          val tweet = entry._2
          tweet.status.getText.contains(keyWord)
        })
      }

      val category1Entries = filterByCategory(batch, category1KeyWords)
      val category2Entries = filterByCategory(batch, category2KeyWords)

      val row1Entries = filterByQuery(category1Entries, queryKeyWord)
      val row2Entries = filterByQuery(category2Entries, queryKeyWord)

      val a = category1Entries.size
      val b = category2Entries.size
      val c = row1Entries.size
      val d = row2Entries.size

      println("batch size: "  + batch.size)
      println("category 1: " + category1KeyWords)
      println("category 2: " + category2KeyWords)
      println("query: " + queryKeyWord)
      println("hits from category 1: " + c + "/" + a)
      println("hits from category 2: " + d + "/" + b)
      println("p value: " + FishersExactTest.test(a, b, c, d))
    }
  }

}
