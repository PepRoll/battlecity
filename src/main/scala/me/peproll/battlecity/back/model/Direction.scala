package me.peproll.battlecity.back.model

trait WithDirection {
  def direction: Direction
}

sealed trait Direction
case object Up extends Direction
case object Down extends Direction
case object Right extends Direction
case object Left extends Direction