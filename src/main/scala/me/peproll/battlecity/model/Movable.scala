package me.peproll.battlecity.model

trait Movable {
  protected def speed: Speed
  def direction: MoveDirection
}

sealed trait Speed {
  def value: Int
}
case object Low extends Speed {
  override def value: Int = 1
}
case object Medium extends Speed {
  override def value: Int = 2
}
case object High extends Speed {
  override def value: Int = 3
}

// speed + direction = change position

sealed trait MoveDirection
case object Up extends MoveDirection
case object Down extends MoveDirection
case object Right extends MoveDirection
case object Left extends MoveDirection
/*
   Speed and health and type bullets depend on tank type.
   Move common logic
 */
case class Tank(position: Coordinates, direction: MoveDirection) extends Movable {
  val speed: Speed = High
}


