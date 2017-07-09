package me.peproll.battlecity.model

private[model] trait Entity {
  def position: Coordinates
}

case class Coordinates(x: Int, y: Int)