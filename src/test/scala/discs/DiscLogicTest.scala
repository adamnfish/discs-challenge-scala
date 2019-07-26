package discs

import org.scalatest.{FreeSpec, Matchers}
import discs.DiscLogic._


class DiscLogicTest extends FreeSpec with Matchers {
  "distanceBetween" - {
    "should return 0 for identical points" in {
      distanceBetween(Point("test1", 0, 0), Point("test2", 0, 0)) shouldEqual 0D
    }

    "should return correct number in simple case" in {
      distanceBetween(Point("test1", 1, 0), Point("test2", 0, 0)) shouldEqual 1D
    }

    "returns correct number in 2D case" in {
      distanceBetween(Point("test1", 4, 1), Point("test2", 1, 5)) shouldEqual 5D
    }
  }

  "max radius" - {
    val p1 = Point("1", 1, 0)

    "is constrained by the nearest point" in {
      maxRadius(Point("test", 0, 0), List(p1), Nil) shouldEqual 1D +- 0.00001
    }

    "is constrained further by a disc" in {
      maxRadius(Point("test", 0, 0), List(p1), List(Disc(p1, 0.5))) shouldEqual 0.5 +- 0.00001
    }
  }
}
