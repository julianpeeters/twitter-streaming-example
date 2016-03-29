package example

import sampler.Sampler
import listeners.TwitterListener

object Main {

  def main(args: Array[String]): Unit = {
    val prefilter = "money"
    val timeLimit = 30000

    val sampleRate = 10000
    val cat1 = List("S.F.","San Francisco")
    val cat2 = List("L.A.","Los Angeles")
    val query = "save"

    val sampler = new Sampler(timeLimit, sampleRate, cat1, cat2, query)

    TwitterListener.listen(Some(timeLimit), Some(prefilter), Some(sampler))
  }



}
