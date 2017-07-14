package me.peproll.battlecity

import me.peproll.battlecity.ui.RootUI
import org.scalajs.dom.document

import scala.scalajs.js.JSApp


object App extends JSApp {
  override def main(): Unit = {
    RootUI.component().renderIntoDOM(document.getElementById("root"))
    ()
  }
}
