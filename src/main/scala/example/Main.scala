package example

import listeners.TwitterListener

object Main {

  def main(args: Array[String]) = {
    val timeLimit = 1000
    TwitterListener.listen(Some(timeLimit))
  }



}
