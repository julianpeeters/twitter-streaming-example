package example
package listeners

import models.Tweet
import sampler.Sampler
import stores.TweetStore

import twitter4j.{
  FilterQuery,
  StallWarning,
  Status,
  StatusListener,
  StatusDeletionNotice,
  TwitterStreamFactory
}

object TwitterListener {
  val config = new twitter4j.conf.ConfigurationBuilder()
  .setOAuthConsumerKey("*****")
  .setOAuthConsumerSecret("*****")
  .setOAuthAccessToken("*****")
  .setOAuthAccessTokenSecret("*****")
  .build

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) = {
      TweetStore.accept(Tweet(status))
    //  println(status.getText)
    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) = {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) = {}
    def onException(ex: Exception) = ex.printStackTrace
    def onScrubGeo(arg0: Long, arg1: Long) = {}
    def onStallWarning(warning: StallWarning) = {}
  }

  def listen(
    maybeTimeLimit: Option[Int] = None,
    maybeKeyWordFilter: Option[String] = None,
    maybeSampler: Option[Sampler] = None) = {

    val stream = new TwitterStreamFactory(TwitterListener.config).getInstance
    stream.addListener(TwitterListener.simpleStatusListener)
    maybeKeyWordFilter match {
      case Some(query) => stream.filter(new FilterQuery().track(query))
      case None => stream.sample
    }
    def maybeStartService(maybeSampler: Option[Sampler]) = {
      maybeSampler match {
        case Some(sampler) => sampler.startSlidingWindow
        case None =>
      }
    }

    maybeTimeLimit match {
      case Some(timeLimit) => // stream for a limited time
        maybeStartService(maybeSampler)
        Thread.sleep(timeLimit)
        stream.cleanUp
        stream.shutdown
      case None => maybeStartService(maybeSampler)
    }
  }

}
