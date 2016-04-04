package example
package stores

import models.Tweet
import scala.collection.concurrent.Map

trait Store {
  val tweets: Map[Long, Tweet]
  def accept(tweet: Tweet): Unit
}
