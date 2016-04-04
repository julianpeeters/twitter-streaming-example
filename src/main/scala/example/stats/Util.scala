package example
package stats

import models.Tweet
import scala.collection.Map

object Util {

  def factorial(n: BigDecimal): BigDecimal = {
    if (n <= 1) 1
    else n * factorial(n - 1)
  }

  def filterByLocation(
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
      tweet.status.getText.contains(keyWord) ||
      tweet.status.getText.contains(keyWord.capitalize)
    })
  }

}
