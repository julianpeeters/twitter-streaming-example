package test

import example.listeners.TwitterListener
import example.stores.TweetStore
import org.specs2._

class TwitterStreamingTest extends mutable.Specification {

  "a TwitterListener" should {

    "add tweets to the TweetStore." in {
      val timeLimit = 2000
      TwitterListener.listen(Some(timeLimit), None, None, TweetStore)
      TweetStore.tweets.isEmpty ==== false
    }

    "sample from a stream pre-filtered by keyword." in {
      val timeLimit = 2000
      val word = "money"
      TwitterListener.listen(Some(timeLimit), Some(word), None, TweetStore)
      // `exists` is a hack. Should be `forall` but requires searching links too
      TweetStore.tweets.values.exists(_.status.getText.contains(word)) ==== true
    }

  }

}
