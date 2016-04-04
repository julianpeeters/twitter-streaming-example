package example
package sampler

import models.Tweet
import stores.Store
import stats.StatTest
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.{ MILLISECONDS => MS }
import scala.collection.Map

/** Optional component of TwitterListener. Listener streams tweets into the
  * tweet store, while the sampler takes most-recent batches and performs a
  * test on the rolling batches of tweets.
  */
class Sampler(
  timeLimit: Int,
  sampleRate: Int,
  test: StatTest,
  store: Store) {

  def startSlidingWindow = {
    val e = Executors.newSingleThreadScheduledExecutor()

    val routine = Starter.start(sampleRate, test, store)
    val initialDelay = sampleRate
    val scheduled = e.scheduleAtFixedRate(routine, initialDelay, sampleRate, MS)

    val stop = Stopper.stop(e, scheduled)
    e.schedule(stop, timeLimit, MS)
  }

}
