package me.peproll.battlecity

import me.peproll.battlecity.model._

case class GameContext(userTank: Tank)

object GameContext {

  val terrain = Terrain(600, 800)

  val initialState = GameContext(Tank(Coordinates(terrain.width / 2, terrain.height / 2), Up))

  def userMove(context: GameContext, direction: MoveDirection): GameContext = {
    val position = context.userTank.position
    val speed = context.userTank.speed
    val newPosition = direction match {
      case Up    => Coordinates(position.x, (position.y - speed.value) max 0)
      case Down  => Coordinates(position.x, (position.y + speed.value) min terrain.height)
      case Right => Coordinates((position.x + speed.value) min terrain.width, position.y)
      case Left  => Coordinates((position.x - speed.value) max 0, position.y)
    }
    context.copy(Tank(newPosition, direction))
  }

}
