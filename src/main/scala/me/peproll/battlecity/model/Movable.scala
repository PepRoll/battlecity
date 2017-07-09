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

sealed trait MoveDirection
case object Up extends MoveDirection
case object Down extends MoveDirection
case object Right extends MoveDirection
case object Left extends MoveDirection

sealed trait TankTrack {
  def nextPosition: TankTrack
}

case object FirstPosition extends TankTrack {
  override def nextPosition: TankTrack = SecondPosition
}

case object SecondPosition extends TankTrack {
  override def nextPosition: TankTrack = FirstPosition
}

case class Tank(position: Coordinates,
                direction: MoveDirection,
                track: TankTrack) extends Movable {
  val speed: Speed = High
}


