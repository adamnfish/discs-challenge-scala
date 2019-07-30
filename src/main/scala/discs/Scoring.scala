package discs

import DiscLogic._


object Scoring {
  def validate(submission: String, points: List[Point]): Either[String, Double] = {
    for {
      discs <- parseSubmission(submission, points)
      _ <- validateOverlaps(discs, points)
    } yield DiscLogic.areaOfAnswer(discs)
  }

  private val Submission = "([^,]+,[0-9\\.]+,?)+".r

  def parseSubmission(submission: String, points: List[Point]): Either[String, List[Disc]] = {
    submission
      .split(",")
      .grouped(2)
      .toList
      .map(_.toList)
      .map {
        case label :: radiusStr :: Nil =>
          for {
            radius <- radiusStr.toDoubleOption.toRight(s"Invalid radius, $radiusStr")
            point <- points.find(_.label == label).toRight(s"No point found with label $label")
          } yield Disc(point, radius)
        case _ =>
          Left("Each disc should be represented by `label,radius` e.g. `abc,1.345`")
      }
      .foldRight[Either[String, List[Disc]]](Right(Nil)) { (discResult, acc) =>
        discResult match {
          case Left(err) =>
            acc match {
              case Left(existingErr) =>
                Left(s"$existingErr, $err")
              case Right(_) =>
                Left(err)
            }
          case Right(disc) =>
            acc match {
              case Right(accumulatedDiscs) =>
                Right(disc :: accumulatedDiscs)
              case Left(err) =>
                Left(err)
            }
        }
      }
  }

  def validateOverlaps(submittedDiscs: List[Disc], points: List[Point]): Either[String, Unit] = {
    submittedDiscs
      .map { disc =>
        val max = maxRadius(disc.centre, points,
          submittedDiscs.filterNot(_ == disc) // remove this disc to check what its max size should be
        )
        if (max < disc.radius) {
          println("too big")
          Left(s"Disc ${disc.centre.label} has radius ${disc.radius}, which is larger than the max of ${max}")
        } else {
          Right(())
        }
      }
      .foldRight[Either[String, Unit]](Right(())) { (result, acc) =>
        result match {
          case Left(err) =>
            acc match {
              case Left(existingErr) =>
                Left(s"$existingErr, $err")
              case Right(_) =>
                Left(err)
            }
          case Right(disc) =>
            acc match {
              case Right(accumulatedDiscs) =>
                Right(())
              case Left(err) =>
                Left(err)
            }
        }
      }
  }
}
