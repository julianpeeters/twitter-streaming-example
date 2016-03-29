package example
package sampler

import java.util.concurrent.{ ScheduledExecutorService, ScheduledFuture }

object Stopper {

  def stop(
    exec: ScheduledExecutorService,
    routine: ScheduledFuture[_]) = new Runnable() {

    @Override
    def run() {
      routine.cancel(true)
      exec.shutdown
    }

  }

}
