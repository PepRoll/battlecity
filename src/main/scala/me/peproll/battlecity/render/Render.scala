package me.peproll.battlecity.render

import me.peproll.battlecity.back.model._
import me.peproll.battlecity.back.model.component.Damagable
import me.peproll.battlecity.render.Render.RenderContext
import me.peproll.battlecity.{GameContext, Settings}
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.HTMLImageElement

trait Render[-E] {
  def render(entity: E, ctx: RenderContext): Unit
}

object Render {

  class RenderContext(val canvas: CanvasRenderingContext2D, val sprites: Map[String, HTMLImageElement])

  def apply[E](entity: E, ctx: RenderContext)(implicit r: Render[E]): Unit =
    r.render(entity, ctx)


  implicit val brickRender = new Render[WallBrick] {
    private val size = Settings.size / 2

    override def render(entity: WallBrick, ctx: RenderContext): Unit = {
      damagableRender(entity, size, position => drawImage("wall_brick", position, ctx, size))
    }
  }

  implicit val steelRender = new Render[WallSteel] {
    private val size = Settings.size / 2

    override def render(entity: WallSteel, ctx: RenderContext): Unit = {
      damagableRender(entity, size, position => drawImage("wall_steel", position, ctx, size))
    }
  }

  implicit val waterRender = new Render[Water] {
    override def render(entity: Water, ctx: RenderContext): Unit = {
      drawImage("water_1", entity.position, ctx)
    }
  }

  implicit val treesRender = new Render[Forest] {
    override def render(entity: Forest, ctx: RenderContext): Unit =
      drawImage("forest", entity.position, ctx)
  }

  implicit val blocksRender = new Render[List[Block]] {
    override def render(entity: List[Block], ctx: RenderContext): Unit = {
      entity.foreach({
       case forest: Forest    => Render(forest, ctx)
       case water:  Water     => Render(water, ctx)
       case forest: WallBrick => Render(forest, ctx)
       case forest: WallSteel => Render(forest, ctx)
      })
    }
  }

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

  implicit val gameContextRender = new Render[GameContext] {
    override def render(gameContext: GameContext, ctx: RenderContext): Unit = {
      ctx.canvas.fillStyle = backgroundColor
      ctx.canvas.fillRect(0, 0, 800, 600)

      Render(gameContext.userTank, ctx)
      Render(gameContext.gameObstacles, ctx)
    }
  }

  private def damagableRender[E <: Entity with Damagable](entity: E, offset: Int, action: Coordinates => Unit): Unit = {
    val damage = entity.damage
    val position = entity.position

    if(!damage.leftTop) {
      action(position)
    }

    if(!damage.rightTop) {
      action(position.copy(x = position.x + offset))
    }

    if(!damage.leftBottom) {
      action(position.copy(y = position.y + offset))
    }

    if(!damage.rightBottom) {
      action(position.copy(x = position.x + offset, y = position.y + offset))
    }
  }

  private def drawImage(imageName: String,
                        coord: Coordinates,
                        ctx: RenderContext,
                        size: Int = Settings.size): Unit = {
    val image = ctx.sprites(s"$imageName.png")

    ctx.canvas.drawImage(
      image = image,
      offsetX = 0,
      offsetY = 0,
      width = size,
      height = size,
      canvasOffsetX = coord.x max 0 min (Settings.gameWidth - size),
      canvasOffsetY = coord.y max 0 min (Settings.gameHeight - size),
      canvasImageHeight = size,
      canvasImageWidth = size)
  }

  private val backgroundColor = "rgb(0, 0, 0)"

}