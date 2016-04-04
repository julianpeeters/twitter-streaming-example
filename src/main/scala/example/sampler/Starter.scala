package example
package sampler

import stores.Store
import stats.StatTest
import java.util.concurrent.{ Executors, TimeUnit }
import scala.collection.Map

object Starter {

  def start(
    sampleRate: Int,
    statTest: StatTest,
    store: Store) = new Runnable() {

    @Override
    def run = {
      val now: Long = System.currentTimeMillis
      val timeframe = now - sampleRate.toLong
      val batch = store.tweets.filterKeys(timestamp => timestamp >= timeframe)
      val testResult = statTest.test(batch)

      // print report to console
      println(s"batch size: ${batch.size}")
      statTest.categories.zipWithIndex.foreach(pair => {
        val index = pair._2 + 1
        val category = pair._1
        println(s"category $index: $category")
      })
      println(s"query: ${statTest.query}")
      println(testResult)
    }
  }

}
