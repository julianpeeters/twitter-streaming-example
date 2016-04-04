package example
package stats

import models.Tweet
import scala.collection.Map

trait StatTest {
  val categories: List[List[String]]
  val query: String
  def test(batch: Map[Long, Tweet]): StatTestResult
}

trait StatTestResult {
  val a: Int
  val b: Int
  val c: Int
  val d: Int
  val p: BigDecimal
  def toString: String
}
