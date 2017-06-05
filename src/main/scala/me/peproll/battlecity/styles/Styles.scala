package me.peproll.battlecity.styles

import scalacss.Defaults._

object Styles extends StyleSheet.Inline {
  import dsl._

  val canvas = style(
    background := "url('/../../src/main/resources/images/surface.jpg')"
  )

  this.addToDocument()
}
