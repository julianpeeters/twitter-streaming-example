package test

import example.stats.FishersExactTest
import org.specs2._

class FishersExactTestTest extends mutable.Specification {

  "a Fischer's Exact Test" should {

    "calculate the correct p value" in {
      val cat1 = 1
      val cat2 = 9
      val row1 = 11
      val row2 = 3
      val p = FishersExactTest.test(cat1, cat2, row1, row2)
      p.doubleValue ==== 0.00134607618791223583254812222371786
    }

  }

}
