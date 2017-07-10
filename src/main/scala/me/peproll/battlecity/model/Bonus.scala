package me.peproll.battlecity.model

sealed trait Bonus
// 10 seconds
case object Helmet extends Bonus
case object Watch extends Bonus

// 20 seconds
case object Shovel extends Bonus

case object Grenade extends Bonus
case object Tank extends Bonus
case object Gun extends Bonus