package discs


object DiscLogic {
  def workOutAnswer(points: List[Point]): List[Disc] = {
    // do some logic here instead of this (non-optimal!) hard-coded answer
    //
    // this just takes the first 15 points and arbitrarily puts a disc there
    // with a radius of `20`
    (0 until 50).foldLeft[List[Disc]](Nil){ (accumulatedDiscs, _) =>
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
      .filter(p => !discs.exists(d => d.centre == p))
      .filterNot(point.label == _.label)
    val maxForPoints = nonDiscPoints.foldLeft[Option[Double]](None) { (acc, p) =>
      val distanceToThisPoint = distanceBetween(point, p)
      acc.map(math.min(_, distanceToThisPoint))
        .orElse(Some(distanceToThisPoint))
    }
    val maxForDiscs = discs.foldLeft[Option[Double]](None) { (acc, d) =>
      val distanceToThisDisc = distanceBetween(point, d)
      acc.map(math.min(_, distanceToThisDisc))
        .orElse(Some(distanceToThisDisc))
    }
    (maxForDiscs, maxForPoints) match {
      case (Some(discMax), Some(pointMax)) =>
        math.min(discMax, pointMax)
      case (Some(discMax), None) =>
        discMax
      case (None, Some(pointMax)) =>
        pointMax
      case _ =>
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
