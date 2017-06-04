package me.peproll.battlecity

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._


object Root {

  private val height = 600.px
  private val width = 800.px

  val component = ScalaComponent.builder[Unit]("Battle city")
    .renderStatic(
      <.canvas(
        ^.width := width,
        ^.height := height,
        ^.background := "url('/../../src/main/resources/images/surface.jpg')"
      )
    )
    .build

}
