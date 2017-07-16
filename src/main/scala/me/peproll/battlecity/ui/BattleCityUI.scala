package me.peproll.battlecity.ui

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import me.peproll.battlecity.back.model.{Direction, Down, Left, Right, Up}
import me.peproll.battlecity.render.Render
import me.peproll.battlecity.render.Render.RenderContext
import me.peproll.battlecity.{GameContext, Settings}
import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.{CanvasRenderingContext2D, HTMLImageElement}

object BattleCityUI {

  case class State(gameContext: GameContext, renderContext: Option[RenderContext])
  class Props(val sprites: Map[String, HTMLImageElement])

  private val inititalState: State = State(GameContext.initialState, None)

  class Backend(scope: BackendScope[Props, State]) extends OnUnmount {

    def keydown(e: KeyboardEvent): Callback = {


      def changePosition(direction: Direction)(state: State): State =
        state.copy(gameContext = state.gameContext.userMove(direction))

      e.keyCode match {
        case KeyCode.Up => scope.modState(changePosition(Up))
        case KeyCode.Down => scope.modState(changePosition(Down))
        case KeyCode.Right => scope.modState(changePosition(Right))
        case KeyCode.Left => scope.modState(changePosition(Left))
        case _ => Callback.empty
      }
    }

    def update(state: State): Unit = {
      state.renderContext.foreach { ctx =>
        Render(state.gameContext, ctx)
      }
    }

    def render(props: Props, state: State): TagOf[Canvas] = {
      <.canvas()
    }
  }

  private val component = ScalaComponent.builder[Props]("Battle city")
    .initialState(inititalState)
    .renderBackend[Backend]
    .componentDidMount(scope => {
      val canvas = scope.getDOMNode.asInstanceOf[Canvas]
      canvas.height = Settings.gameHeight
      canvas.width = Settings.gameWidth
      val renderContext = new RenderContext(
        canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D],
        scope.props.sprites
      )
      scope.modState(_.copy(renderContext = Some(renderContext)))
    })
    .componentWillUpdate($ => Callback($.backend.update($.nextState)))
    .configure(EventListener[dom.KeyboardEvent].install(
      "keydown",
      $ => e => $.backend.keydown(e),
      _ => dom.window)
    )
    .build

  def apply(sprites: Map[String, HTMLImageElement]) =
    component.withKey("root")(new Props(sprites))

}
