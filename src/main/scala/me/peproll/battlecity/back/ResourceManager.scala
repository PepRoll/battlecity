package me.peproll.battlecity.back

import me.peproll.battlecity.Settings
import me.peproll.battlecity.utils.Counter
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLImageElement

import scala.async.Async.{async, await}
import scala.concurrent.{Future, Promise}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.util.{Failure, Success}

trait IResourceManager {
  def initImages: Counter[Map[String, HTMLImageElement]]
}

object ResourceManager extends IResourceManager {

  override def initImages: Counter[Map[String, HTMLImageElement]] = Counter { counter =>
    val rootResources: String = "/src/main/resources/sprites/"

    val loadImages = for (sprite <- Settings.sprites) yield async {
      val image = await(loadImage(rootResources + sprite))
      await(counter.increment())
      sprite -> image
    }

    Future.sequence(loadImages).map(_.toMap) onComplete {
      case Success(map) =>
        counter.success(map)
      case Failure(ex) =>
        counter.failure(ex)
    }
  }

  private def loadImage(src: String): Future[HTMLImageElement] = {
    val promise = Promise[HTMLImageElement]
    val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    element.onload = _ => promise.success(element)
    element.src = src
    promise.future
  }

}
