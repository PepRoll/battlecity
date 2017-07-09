package me.peproll.battlecity.view

import me.peproll.battlecity.model._
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLImageElement


trait Render[T] {
  def render(entity: T, ctx: RenderContext): Unit
}

class RenderContext(val canvas: CanvasRenderingContext2D, val sprites: HTMLImageElement)

object Render {

  def show[T](entity: T, ctx: RenderContext)(implicit r: Render[T]): Unit =
    r.render(entity, ctx)

  implicit val tankRender = new Render[Tank] {

    private val dimension = 25

    private val showDirection: Map[(MoveDirection, TankTrack), (Int, Int)] = Map(
      ((Up, FirstPosition), (0, 0)),
      ((Up, SecondPosition), (16, 0)),
      ((Left, FirstPosition), (32, 0)),
      ((Left, SecondPosition), (48, 0)),
      ((Down, FirstPosition), (64, 0)),
      ((Down, SecondPosition), (80, 0)),
      ((Right, FirstPosition), (95, 0)),
      ((Right, SecondPosition), (111, 0))
    )

    override def render(tank: Tank, ctx: RenderContext): Unit = {
      val (xSrcImg, ySrcImg) = showDirection((tank.direction, tank.track))
      ctx.canvas.drawImage(
        image = ctx.sprites,
        offsetX = xSrcImg,
        offsetY = ySrcImg,
        width = 15,
        height = 15, (tank.position.x - dimension) max 0, (tank.position.y - dimension) max 0, dimension, dimension
      )
    }
  }

}
