package me.peproll.battlecity.render

import me.peproll.battlecity.Settings
import me.peproll.battlecity.back.model._
import me.peproll.battlecity.render.Render.RenderContext
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLImageElement

trait Render[-E] {
  def render(entity: E, ctx: RenderContext): Unit
}

object Render {

  class RenderContext(val canvas: CanvasRenderingContext2D, val sprites: Map[String, HTMLImageElement])

  def apply[E](entity: E, ctx: RenderContext)(implicit r: Render[E]): Unit =
    r.render(entity, ctx)

  implicit val playerTankRender = new Render[PlayerTank] {

    private val prefix: String = "tank_player1"

    def direction(d: Direction): String = d match {
      case Up => "up"
      case Down => "down"
      case Left => "left"
      case Right => "right"
    }

    def tankTrack(t: TankTrack): String = t match {
      case FirstPosition => "t1"
      case SecondPosition => "t2"
    }

    override def render(tank: PlayerTank, ctx: RenderContext): Unit = {
      val name = Seq(
        prefix,
        direction(tank.direction),
        "c0",
        tankTrack(tank.tankTrack)
      ).mkString("_")

      drawImage(name, tank.position, ctx)
    }
  }

  private def drawImage(imageName: String,
                        coord: Coordinates,
                        ctx: RenderContext): Unit = {
    val image = ctx.sprites(s"$imageName.png")

    ctx.canvas.drawImage(
      image = image,
      offsetX = 0,
      offsetY = 0,
      width = Settings.size,
      height = Settings.size,
      canvasOffsetX = (coord.x - Settings.size) max 0,
      canvasOffsetY = (coord.y - Settings.size) max 0,
      canvasImageHeight = Settings.size,
      canvasImageWidth = Settings.size)
  }

}