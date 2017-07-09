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

    private val showDirection: Map[MoveDirection, (Int, Int)] = Map(
      (Up, (0, 0)),
      (Left, (31, 0)),
      (Down, (65, 0)),
      (Right, (95, 0))
    )

    override def render(tank: Tank, ctx: RenderContext): Unit = {
      val (xSrcImg, ySrcImg) = showDirection(tank.direction)
      ctx.canvas.drawImage(
        ctx.sprites, xSrcImg, ySrcImg, 15, 15, (tank.position.x - 35) max 0, (tank.position.y - 35) max 0, 35, 35
      )
    }
  }

}
