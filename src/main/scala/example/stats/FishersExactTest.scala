package example
package stats

import models.Tweet
import scala.collection.Map

case class FishersExactTestResult(
  a: Int,
  b: Int,
  c: Int,
  d: Int,
  p: BigDecimal) extends StatTestResult {
    override def toString = s"""
      |hits from category 1: $c/$a
      |hits from category 2: $d/$b
      |p value: $p
      |
      """.trim.stripMargin
  }

/** Probability the observed difference is due to effects of chance, for 2x2.
  * adapted from https://en.wikipedia.org/wiki/Fisher%27s_exact_test
  * NOTE: for large values, consider using a Chi-Squared test instead.
  *
  *       (a + b)(c + d)
  *       (  a  )(  c  )     (a + b)!(c + d)!(a + c)!(b + d)!
  *  p = ---------------- = ----------------------------------
  *          (  n  )                 a! b! c! d! n!
  *          (a + c)
  */
case class FishersExactTest(
  categories: List[List[String]],
  query: String) extends StatTest {

  def test(batch: Map[Long, Tweet]): FishersExactTestResult = {
    val category1Entries = Util.filterByLocation(batch, categories(0))
    val category2Entries = Util.filterByLocation(batch, categories(1))

    val hits1Entries = Util.filterByQuery(category1Entries, query)
    val hits2Entries = Util.filterByQuery(category2Entries, query)

    val a = hits1Entries.size
    val b = hits2Entries.size
    val c = category1Entries.size - a
    val d = category2Entries.size - b

    val p = pValue(a, b, c, d)

    FishersExactTestResult(a, b, c, d, p)
  }


  def pValue(a: Int, b: Int, c: Int, d: Int): BigDecimal = {
    val n = a + b + c + d

    val e = Util.factorial(a + b)
    val f = Util.factorial(c + d)
    val g = Util.factorial(a + c)
    val h = Util.factorial(b + d)

    val i = Util.factorial(a)
    val j = Util.factorial(b)
    val k = Util.factorial(c)
    val l = Util.factorial(d)

    val m = Util.factorial(n)

    val p = (e * f * g * h)/(i * j * k * l * m)
    p
  }


}
