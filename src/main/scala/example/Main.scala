package example

import listeners.TwitterListener
import sampler.Sampler
import stats.FishersExactTest
import stores.{ TweetStore => TS }

object Main {

  def main(args: Array[String]): Unit = {
    val prefilter = "money"
    val timeLimit = 5000

    val sampleRate = 1000
    val location1 = List("S.F.", "San Francisco")
    val location2 = List("L.A.", "Los Angeles")
    val query = "save"

    val statTest = FishersExactTest(List(location1, location2), query)

    val sampler = new Sampler(timeLimit, sampleRate, statTest, TS)

    // ingest tweets and periodically analyze batches with a sliding window
    TwitterListener.listen(Some(timeLimit), Some(prefilter), Some(sampler), TS)
  }



}
