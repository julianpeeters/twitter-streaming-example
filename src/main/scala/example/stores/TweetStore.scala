package example
package stores

import models.Tweet
import java.util.concurrent.ConcurrentHashMap
import scala.collection.convert.Wrappers.JConcurrentMapWrapper

object TweetStore {

  val tweets: scala.collection.concurrent.Map[Long, Tweet] = {
  	JConcurrentMapWrapper(new ConcurrentHashMap[Long, Tweet]())
  }

  def accept(tweet: Tweet) {
    val timestamp: Long = System.currentTimeMillis
    if (!tweets.contains(timestamp)) {
      val _ = tweets += timestamp -> tweet
    }
  }

}
