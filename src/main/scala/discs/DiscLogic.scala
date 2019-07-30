package discs


object DiscLogic {
  def workOutAnswer(points: List[Point]): List[Disc] = {
    // run step 50 times (or as many times as we can for the number of points)
    // each time, find the largest possible disc
    (0 until math.min(50, points.length))
      .foldLeft[List[Disc]](Nil){ (accumulatedDiscs, _) =>
        nextBestDisc(points, accumulatedDiscs) :: accumulatedDiscs
      }
  }

  def nextBestDisc(points: List[Point], discs: List[Disc]): Disc = {
    val answers = points.map { p =>
      Disc(p, maxRadius(p, points, discs))
    }
    answers.maxBy(_.radius)
  }

  def maxRadius(point: Point, points: List[Point], discs: List[Disc]): Double = {
    val nonDiscPoints = points
      .filterNot(p => discs.exists(d => d.centre == p)) // exclude any points with a disc, we'll consider them next
      .filterNot(point.label == _.label) // exclude the point itself, otherwise we'd always get 0
    val maxForPoints = nonDiscPoints.foldLeft[Option[Double]](None) { (acc, p) =>
      val distanceToPoint = distanceBetween(point, p)
      acc.map(math.min(_, distanceToPoint))
        .orElse(Some(distanceToPoint))
    }
    val maxForDiscs = discs.foldLeft[Option[Double]](None) { (acc, d) =>
      val distanceToDisc = distanceBetween(point, d)
      acc.map(math.min(_, distanceToDisc))
        .orElse(Some(distanceToDisc))
    }
    (maxForDiscs, maxForPoints) match {
      case (Some(discMax), Some(pointMax)) =>
        math.min(discMax, pointMax)
      case (Some(discMax), None) =>
        discMax
      case (None, Some(pointMax)) =>
        pointMax
      case _ =>
        // this will only happen if there are no discs or points
        // this means we're unconstrained, so we can set an arbitrarily large disc!
        10000000000000000D
    }
  }

  def distanceBetween(p1: Point, p2: Point): Double = {
    math.sqrt(math.pow(p1.x - p2.x, 2) + math.pow(p1.y - p2.y, 2))
  }

  def distanceBetween(p1: Point, disc: Disc): Double = {
    val distanceToCentre = distanceBetween(p1, disc.centre)
    distanceToCentre - disc.radius
  }

  // https://www.google.com/search?q=area+of+circle&oq=area+of+circle
  def areaOfDisc(disc: Disc): Double = {
    math.Pi * math.pow(disc.radius, 2)
  }
}
