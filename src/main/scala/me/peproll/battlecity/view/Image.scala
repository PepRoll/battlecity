package me.peproll.battlecity.view

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLImageElement

class Image(src: String, onLoad: HTMLImageElement => Unit) {

  private val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  element.onload = (e: dom.Event) => {
    onLoad(element)
  }
  element.src = src

}
