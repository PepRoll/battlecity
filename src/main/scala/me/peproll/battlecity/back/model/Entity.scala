package me.peproll.battlecity.back.model

import me.peproll.battlecity.back.model.component.Transition

trait Entity {
  def position: Coordinates
}

case class Coordinates(x: Int, y: Int) {
  def tuple: (Int, Int) = (x, y)
}
case class Time(seconds: Int)

abstract class Block extends Entity
  with Transition

case class Wall(position: Coordinates) extends Block {
  override def transition: Boolean = false
}

case class Monolith(position: Coordinates) extends Block {
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