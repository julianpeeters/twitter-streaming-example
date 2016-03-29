package example
package sampler

import models.Tweet
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.MILLISECONDS
import scala.collection.Map

class Sampler(timeLimit: Int,
  sampleRate: Int,
  category1: List[String],
  category2: List[String],
  queryKeyWord: String) {

  def startSlidingWindow = {
    val e = Executors.newSingleThreadScheduledExecutor()

    val routine = Starter.start(sampleRate, category1, category2, queryKeyWord)
    val scheduled = e.scheduleAtFixedRate(routine, 0, sampleRate, MILLISECONDS)

    val stop = Stopper.stop(e, scheduled)
    e.schedule(stop, timeLimit, MILLISECONDS)
  }

}
