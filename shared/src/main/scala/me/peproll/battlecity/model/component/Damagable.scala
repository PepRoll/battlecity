package me.peproll.battlecity.model.component

trait Damagable {
  def damage: Damage
}

case class Damage(leftTop: Boolean,
                  rightTop: Boolean,
                  leftBottom: Boolean,
                  rightBottom: Boolean)