package me.peproll.battlecity

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

object Settings {
  val sprites: Set[String] = SettingsJS.sprites.toSet
  val size: Int = SettingsJS.size
  val gameWidth: Int = size * 16
  val gameHeight: Int = size * 14
}

@js.native
@JSGlobal("settings")
object SettingsJS extends js.Any {
  def size: Int = js.native
  def sprites: js.Array[String] = js.native
}
