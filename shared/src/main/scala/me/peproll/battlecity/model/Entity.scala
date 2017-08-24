package me.peproll.battlecity.model

import me.peproll.battlecity.model.component._

trait Entity {
  def position: Coordinates
}

case class Coordinates(x: Int, y: Int) {
  def tuple: (Int, Int) = (x, y)
}
case class Time(seconds: Int)

abstract class Block extends Entity
  with Transition

case class WallBrick(position: Coordinates, damage: Damage) extends Block
  with Damagable {
  override def transition: Boolean = false
}

case class WallSteel(position: Coordinates, damage: Damage) extends Block
  with Damagable {
  override def transition: Boolean = false
}

case class Forest(position: Coordinates) extends Block {
  override def transition: Boolean = true
}

case class Water(position: Coordinates) extends Block {
  override def transition: Boolean = false
}

case class Ice(position: Coordinates) extends Block {
  override def transition: Boolean = true
}