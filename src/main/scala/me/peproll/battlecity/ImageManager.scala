package me.peproll.battlecity

import org.scalajs.dom.raw.HTMLImageElement

import scala.concurrent.Future

trait IImageManager {
  def loadImage(src: String): Future[HTMLImageElement]
}

object ImageManager extends IImageManager {



  //private val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  //element.onload = (e: dom.Event) => {
  //  onLoad(element)
  //}
  //element.src = src

  override def loadImage(src: String): Future[HTMLImageElement] = {

  }
}
