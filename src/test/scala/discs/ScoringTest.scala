package discs

import org.scalatest.{FreeSpec, Matchers}

import Scoring._


class ScoringTest extends FreeSpec with Matchers {
  "parseSubmission" - {
    val points = List(
      Point("test1", 1, 1),
      Point("test2", 1, 1),
    )

    "parses a submission" in {
      parseSubmission("test1,1.5,test2,2.8", points)
    }

    "fails if the submission contains a non-existent point (by label)" in {
      parseSubmission("bad-label,1.5,test2,2.8", points)
    }

    "fails if the submission invalid characters" in {
      parseSubmission("bad-label,1.5foo,test2,2.8", points)
    }
  }
}
