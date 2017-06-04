package me.peproll.battlecity

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._


object Root {

  val component = ScalaComponent.builder[Unit]("Battle city")
  .renderStatic(<.div("Battle city"))
  .build

}
