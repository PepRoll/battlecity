package me.peproll.battlecity.model.component

trait ArmorComponent {
  def armor: Armor
}

sealed trait Armor
case object LowArmor extends Armor
case object MediumArmor extends Armor
case object HeavyArmor extends Armor