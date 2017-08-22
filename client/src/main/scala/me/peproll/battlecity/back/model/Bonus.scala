package me.peproll.battlecity.back.model

sealed trait Bonus
// 10 seconds
case object HelmetBonus extends Bonus
case object WatchBonus extends Bonus

// 20 seconds
case object ShovelBonus extends Bonus

case object GrenadeBonus extends Bonus
case object TankBonus extends Bonus
case object GunBonus extends Bonus