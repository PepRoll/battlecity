package me.peproll.battlecity

import japgolly.scalajs.react.extra.{EventListener, OnUnmount}
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, Callback, _}
import me.peproll.battlecity.model.{Direction, Down, Left, Right, Up}
import me.peproll.battlecity.view.Render.show
import me.peproll.battlecity.view.{Image, RenderContext}
import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html.Canvas
import org.scalajs.dom.raw.CanvasRenderingContext2D

object Root {

  class Backend(scope: BackendScope[Unit, GameContext]) extends OnUnmount {

    private var renderContext: Option[RenderContext] = None

    def keydown(e: KeyboardEvent): Callback = {

      def changePosition(state: GameContext, moveDirection: Direction): GameContext = {
        val ctx = GameContext.userMove(state, moveDirection)
        update(ctx)
        ctx
      }

      e.keyCode match {
        case KeyCode.Up => scope.modState(changePosition(_, Up))
        case KeyCode.Down => scope.modState(changePosition(_, Down))
        case KeyCode.Right => scope.modState(changePosition(_, Right))
        case KeyCode.Left => scope.modState(changePosition(_, Left))
        case _ => Callback.empty
      }
    }

    def mount(canvas: Canvas): Callback = Callback {
      canvas.height = 600
      canvas.width = 800
      new Image("/src/main/resources/images/sprites.png", { img =>
        renderContext = Some(new RenderContext(
          canvas = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D],
          sprites = img
        ))
        scope.modState(state => {
          update(state)
          state
        }).runNow()
      })
    }

    def update(state: GameContext): Unit = {
      renderContext.foreach { ctx =>
        ctx.canvas.fillStyle = "rgb(0, 0, 0)"
        ctx.canvas.fillRect(0, 0, 800, 600)

        show(state.userTank, ctx)
        state.walls.foreach(wall => show(wall, ctx))
      }
    }

    def render(props: Unit, state: GameContext) = <.canvas()
  }

  val component = ScalaComponent.builder[Unit]("Battle city")
    .initialState(GameContext.initialState)
    .renderBackend[Backend]
    .componentDidMount(scope => {
      val canvas = scope.getDOMNode.asInstanceOf[Canvas]
      scope.backend.mount(canvas)
    })
    .configure(EventListener[dom.KeyboardEvent].install(
      "keydown",
      $ => e => $.backend.keydown(e),
      _ => dom.window)
    )
    .build

}
