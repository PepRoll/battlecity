package me.peproll.battlecity.model

import me.peproll.battlecity.model.component.TransitionComponent

trait Entity {
  def position: Coordinates
}

case class Coordinates(x: Int, y: Int)
case class Time(seconds: Int)

abstract class Block extends Entity
  with TransitionComponent

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