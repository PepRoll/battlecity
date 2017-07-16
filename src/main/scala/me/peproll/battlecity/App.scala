package me.peproll.battlecity

import me.peproll.battlecity.ui.BattleCityUI
import org.scalajs.dom.document

import scala.scalajs.js.JSApp


object App extends JSApp {
  override def main(): Unit = {

    BattleCityUI.component().renderIntoDOM(document.getElementById("root"))
    ()
  }
}
