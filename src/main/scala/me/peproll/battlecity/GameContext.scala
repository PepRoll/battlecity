package me.peproll.battlecity

import me.peproll.battlecity.model._

case class GameContext(userTank: Tank,
                       walls: Seq[Wall])

object GameContext {

  val terrain = Terrain(600, 800)

  val initialState = GameContext(
    Tank(Coordinates(terrain.width / 2, terrain.height / 2), Up, FirstPosition),
    Seq(
      Wall(Coordinates(10, 10)),
      Wall(Coordinates(20, 20))
    )
  )

  def userMove(context: GameContext, direction: Direction): GameContext = {
    val position = context.userTank.position
    val speed = context.userTank.speed
    val newPosition = direction match {
      case Up    => Coordinates(position.x, (position.y - speed.value) max 0)
      case Down  => Coordinates(position.x, (position.y + speed.value) min Constants.height)
      case Right => Coordinates((position.x + speed.value) min terrain.width, position.y)
      case Left  => Coordinates((position.x - speed.value) max 0, position.y)
    }
    context.copy(Tank(newPosition, direction, context.userTank.track.nextPosition))
  }

}
