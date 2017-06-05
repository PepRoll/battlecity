package me.peproll.battlecity

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import me.peproll.battlecity.styles.Styles

object Root {

  val component = ScalaComponent.builder[Unit]("Battle city")
    .renderStatic(
      <.canvas(
        ^.className := Styles.canvas.htmlClass,
        ^.width := 800.px,
        ^.height := 600.px
      )
    )
    .build

}
