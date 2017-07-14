package me.peproll.battlecity.ui
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, _}
import me.peproll.battlecity.back.ResourceManager
import org.scalajs.dom.raw.HTMLImageElement

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object RootUI {

  sealed trait State
  case class Loading(percent: Int) extends State
  case class OpenGame(sprites: Map[String, HTMLImageElement]) extends State

  val component = ScalaComponent.builder[Unit]("LoadResourcesUI")
    .initialState[State](Loading(0))
    .render_S {
      case Loading(percent) => <.div(s"state images - $percent")
      case OpenGame(sprites) => BattleCityUI(sprites).vdomElement
    }
    .componentDidMount($ => Callback.future {
      ResourceManager.initImages.run { next =>
        $.setState(Loading(next)).toFuture
      }.map(sprites => $.setState(OpenGame(sprites)))
    })
    .build

}
