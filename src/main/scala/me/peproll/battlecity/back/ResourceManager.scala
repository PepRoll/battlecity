package me.peproll.battlecity.back

import me.peproll.battlecity.Settings
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLImageElement

import scala.concurrent.{Future, Promise}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

trait IResourceManager {
  def initImages: Future[Map[String, HTMLImageElement]]
}

object ResourceManager extends IResourceManager {

  override def initImages: Future[Map[String, HTMLImageElement]] = {
    val rootResources: String = "/src/main/resources/sprites/"

    Future.traverse(Settings.sprites){ sprite =>
      loadImage(rootResources + sprite).map(image => sprite -> image)
    }.map(_.toMap)
  }

  private def loadImage(src: String): Future[HTMLImageElement] = {
    val promise = Promise[HTMLImageElement]
    val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    element.onload = _ => promise.success(element)
    element.src = src
    promise.future
  }

}
