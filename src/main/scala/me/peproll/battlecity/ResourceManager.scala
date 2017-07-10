package me.peproll.battlecity

import me.peproll.battlecity.model._
import me.peproll.battlecity.model.component.{Armor, LowArmor, MediumArmor}
import me.peproll.battlecity.view.Image


trait SelectImage[E] {
  def image(entity: E): Image
}

object ResourceManager {

  private val rootResources: String = "/src/main/resources/sprites/"

  private var nameToImages: Map[String, Image] = _

  def loadResources() = {
    val builder = Map[String, Image]
  }


  def filename(name: String): String = s"$name.png"

  implicit val playerTankImage = new SelectImage[PlayerTank] {
    import TankHelper._

    private val prefix: String = "tank_player1"

    override def image(tank: PlayerTank): Image = {
      val name = Seq(
        prefix,
        direction(tank.direction),
        armor(tank.rank.armor),
        tankTrack(tank.tankTrack)
      ).mkString("_")

      nameToImages(filename(name))
    }
  }

  private object TankHelper {

    def armor(a: Armor): String = a match {
      case LowArmor => "c2"
      case MediumArmor => "c1"
      case LowArmor => "c0"
    }

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
  }


}
