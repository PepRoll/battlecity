package me.peproll.battlecity.back

import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.extra.Broadcaster

object GameBroadcast extends Broadcaster[GameState] {
  private[back] def change(gameContext: GameState): Callback = broadcast(gameContext)
}
