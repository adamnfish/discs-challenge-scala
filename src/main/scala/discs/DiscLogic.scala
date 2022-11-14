package discs


object DiscLogic {
  /**
   * Your implementation should go here!
   *
   * Your goal is to return a List of up to 50 discs with the largest
   * possible total area. The discs must not overlap each other, or any
   * of the points.
   *
   * Please refer to the README file for detailed instructions.
   *
   * Note: the area covered by a Disc can be calculated as follows:
   * Given a Disc, `disc`:
   *
   *   math.Pi * math.pow(disc.radius, 2)
   *
   * https://www.google.com/search?q=area+of+circle&oq=area+of+circle
   */
  def workOutAnswer(points: List[Point]): List[Disc] = {
    // do some logic here instead of this (non-optimal!) hard-coded answer
    //
    // this just takes the first 15 points and arbitrarily puts a disc there
    // with a radius of `20`
    points
      .take(15)
      .map(Disc(_, 20))
  }
}
