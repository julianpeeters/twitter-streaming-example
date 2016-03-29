package example
package stats

object FishersExactTest {

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
  def test(a: Int, b: Int, c: Int, d: Int): BigDecimal =  {

    def factorial(n: BigDecimal): BigDecimal = {
      if (n <= 1) 1
      else n * factorial(n - 1)
    }

    val n = a + b + c + d

    val e = factorial(a + b)
    val f = factorial(c + d)
    val g = factorial(a + c)
    val h = factorial(b + d)

    val i = factorial(a)
    val j = factorial(b)
    val k = factorial(c)
    val l = factorial(d)

    val m = factorial(n)

    val p = (e * f * g * h)/(i * j * k * l * m)
    p
  }
}
