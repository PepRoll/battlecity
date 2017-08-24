package me.peproll.battlecity.ui

import japgolly.scalajs.react.extra.{EventListener, Listenable, OnUnmount}
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import me.peproll.battlecity.Settings
import me.peproll.battlecity.model.component._
import me.peproll.battlecity.back.{GameBroadcast, GameManager, GameState, ResourceManager}
import me.peproll.battlecity.render.Render
import me.peproll.battlecity.render.Render.RenderContext
import me.peproll.battlecity.ui.BattleCityUI.State.{Game, Loading}
import monocle.macros.{GenLens, GenPrism}
import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLElement}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object BattleCityUI {

  sealed trait State
  object State {
    case object Loading extends State
    case class Game(gameContext: GameState, renderContext: RenderContext) extends State

    val gameContextLens = GenPrism[State, Game] composeLens GenLens[Game](_.gameContext)
    val canvasLens = GenPrism[State, Game] composeLens GenLens[Game](_.renderContext) composeLens GenLens[RenderContext](_.canvas)
  }

  private val inititalState: State = Loading

  class Backend(scope: BackendScope[Unit, State]) extends OnUnmount {

    def keyDown(e: KeyboardEvent): Callback = {

      def changePosition(direction: Direction): Callback =
        GameManager.playerMove(direction)

      e.keyCode match {
        case KeyCode.Up    => changePosition(Up)
        case KeyCode.Down  => changePosition(Down)
        case KeyCode.Right => changePosition(Right)
        case KeyCode.Left  => changePosition(Left)
        case KeyCode.Space => GameManager.playerFire
        case _ => Callback.empty
      }
    }

    def update(state: State): Unit = state match {
      case Game(gameContext, renderContext) => Render(gameContext, renderContext)
      case _ =>
    }


    def render(props: Unit, state: State): TagOf[HTMLElement] = {
      state match {
        case Loading => <.div("Loading...")
        case _: Game => <.canvas()
      }
    }
  }

  val component = ScalaComponent.builder[Unit]("Battle city")
    .initialState(inititalState)
    .renderBackend[Backend]
    .componentDidMount(scope => Callback.future {
      ResourceManager.initImages.map(sprites => scope.setState(
        Game(GameManager.game, RenderContext(canvas = None, sprites)))
      )
    })
    .componentDidUpdate(scope => scope.currentState match {
      case Game(_, RenderContext(None, _)) =>
        val canvas = scope.getDOMNode.asInstanceOf[Canvas]
        canvas.height = Settings.gameHeight
        canvas.width = Settings.gameWidth
        scope.modState(State.canvasLens.set(Some(
          canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]))
        )
      case _ => Callback.empty
    })
    .componentWillUpdate($ => Callback($.backend.update($.nextState)))
    .configure(EventListener[dom.KeyboardEvent].install(
      "keydown",
      $ => e => $.backend.keyDown(e),
      _ => dom.window)
    )
    .configure(Listenable.listen(
      listenable = _ => GameBroadcast,
      makeListener = $ => (game: GameState) => $.modState(State.gameContextLens.set(game))))
    .build

}
