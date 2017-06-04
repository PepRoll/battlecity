package me.peproll.battlecity

import org.scalajs.dom.document

import scala.scalajs.js.JSApp


object App extends JSApp {
  override def main(): Unit =
    Root.component().renderIntoDOM(document.getElementById("root"))
}
