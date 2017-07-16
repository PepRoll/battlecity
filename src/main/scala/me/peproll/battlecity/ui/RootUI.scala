package me.peproll.battlecity.ui
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, _}
import me.peproll.battlecity.back.ResourceManager
import org.scalajs.dom.raw.HTMLImageElement

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object RootUI {

  sealed trait State
  case object Loading extends State
  case class Game(sprites: Map[String, HTMLImageElement]) extends State

  val component = ScalaComponent.builder[Unit]("LoadResourcesUI")
    .initialState[State](Loading)
    .render_S {
      case Loading => <.div(s"Loading...")
      case Game(sprites) => BattleCityUI(sprites).vdomElement
    }
    .componentDidMount($ => Callback.future {
      ResourceManager.initImages.map(sprites => $.setState(Game(sprites)))
    })
    .build

}
