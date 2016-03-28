package test

import example.listeners.TwitterListener
import example.stores.TweetStore
import org.specs2._

class TwitterStreamingTest extends mutable.Specification {

  "a TwitterListener" should {

    "add tweets to the TweetStore." in {
      val timeLimit = 2000
      TwitterListener.listen(Some(timeLimit))
      TweetStore.tweets.isEmpty ==== false
    }

  }

}
