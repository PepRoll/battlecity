package me.peproll.battlecity

import me.peproll.battlecity.back.model._
import me.peproll.battlecity.back.model.component.Damage

final case class GameContext(userTank: PlayerTank,
                             gameObstacles: List[Block]) {

  def userMove(direction: Direction): GameContext = {
    val (x, y) = userTank.position.tuple
    val speed = userTank.rank.speed
    val newPosition = direction match {
      case Up    => Coordinates(x, (y - speed.value) max 0)
      case Down  => Coordinates(x, (y + speed.value) min Settings.gameHeight)
      case Right => Coordinates((x + speed.value) min Settings.gameWidth, y)
      case Left  => Coordinates((x - speed.value) max 0, y)
    }
    this.copy(userTank.copy(position = newPosition, direction = direction, userTank.tankTrack.next))
  }

}

object GameContext {

  def initialState: GameContext = GameContext(
    userTank = PlayerTank(
      position = Coordinates(Settings.gameWidth / 2, Settings.gameHeight / 2),
      direction = Up,
      tankTrack = FirstPosition,
      shield = false,
      rank = Solder),
    gameObstacles = List[Block](
      Forest(position = Coordinates(0, 0)),
      WallSteel(position = Coordinates(Settings.size, 0), damage = Damage(true, false, false, false)),
      WallBrick(position = Coordinates(Settings.size * 2, 0), damage = Damage(false, false, true, false)),
      Water(position = Coordinates(Settings.size * 3, 0))
    )
  )

}
