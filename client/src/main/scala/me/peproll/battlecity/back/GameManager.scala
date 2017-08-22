package me.peproll.battlecity.back

import japgolly.scalajs.react.Callback
import me.peproll.battlecity.Settings
import me.peproll.battlecity.back.model._
import me.peproll.battlecity.back.model.component._

final case class GameState(userTank: PlayerTank,
                           bullets: List[Bullet],
                           gameObstacles: List[Block])

object GameManager {

  private var gameState: GameState = initialState

  def game: GameState = gameState

  def playerMove(direction: Direction): Callback = {
    val tank = game.userTank
    val (x, y) = tank.position.tuple
    val speed = tank.rank.speed
    val newPosition = direction match {
      case Up    => Coordinates(x, (y - speed.value) max 0)
      case Down  => Coordinates(x, (y + speed.value) min Settings.gameHeight)
      case Right => Coordinates((x + speed.value) min Settings.gameWidth, y)
      case Left  => Coordinates((x - speed.value) max 0, y)
    }
    gameState =
      game.copy(game.userTank.copy(position = newPosition, direction = direction, game.userTank.tankTrack.next))
    GameBroadcast.change(gameState)
  }

  def playerFire: Callback = {
    val (x, y) = game.userTank.position.tuple
    val tankAdjustment = 6
    val offset = 12
    val position = game.userTank.direction match {
      case Up => Coordinates(x + offset, y - tankAdjustment)
      case Down => Coordinates(x + offset, y + Settings.size)
      case Left => Coordinates(x - tankAdjustment, y + offset)
      case Right => Coordinates(x + Settings.size, y + offset)
    }
    val bullet = Bullet(position, game.userTank.direction, game.userTank.bullet)
    gameState = game.copy(bullets = game.bullets :+ bullet)
    GameBroadcast.change(gameState)
  }

  private def initialState: GameState = GameState(
    userTank = PlayerTank(
      position = Coordinates(Settings.gameWidth / 2, Settings.gameHeight / 2),
      direction = Up,
      tankTrack = FirstPosition,
      shield = false,
      rank = Solder),
    bullets = List.empty,
    gameObstacles = List[Block](
      Forest(position = Coordinates(0, 0)),
      WallSteel(position = Coordinates(Settings.size, 0), damage = Damage(true, false, false, false)),
      WallBrick(position = Coordinates(Settings.size * 2, 0), damage = Damage(false, false, true, false)),
      Water(position = Coordinates(Settings.size * 3, 0))
    )
  )

}
