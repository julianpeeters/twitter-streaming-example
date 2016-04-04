package test

import data.TestData
import example.models.Tweet
import example.stats.FishersExactTest
import example.stores.TweetStore

import twitter4j.TwitterObjectFactory

import org.specs2._

class FishersExactTestTest extends mutable.Specification {

  "a Fischer's Exact Test" should {

    "calculate the correct p value given known counts" in {
      val dummyCategories = List(List("loc1"), List("loc2"))
      val dummyQuery = "q"
      val statTest = new FishersExactTest(dummyCategories, dummyQuery)
      val a = 1
      val b = 9
      val c = 11
      val d = 3
      val p = statTest.pValue(a, b, c, d)

      p.doubleValue ==== 0.00134607618791223583254812222371786
    }

    "calculate the correct p value of a known batch of tweets" in {
      val statuses = TestData.rawJsons.map(TwitterObjectFactory.createStatus(_))
      TweetStore.tweets.clear
      statuses.foreach(status => {
        Thread.sleep(1)
        TweetStore.accept(Tweet(status))
      })

      val location1 = List("S.F.", "San Francisco")
      val location2 = List("L.A.", "Los Angeles")
      val query = "save"

      val batch = TweetStore.tweets
      val statTest = FishersExactTest(List(location1, location2), query)
      val result = statTest.test(batch)

      result.p.doubleValue ==== 0.00134607618791223583254812222371786
    }

  }

}
