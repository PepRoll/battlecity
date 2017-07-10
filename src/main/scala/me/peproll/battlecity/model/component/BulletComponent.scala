package me.peproll.battlecity.model.component

trait BulletComponent {
  def bullet: Bullet
  def bulletCount: Int
}

sealed trait Power
case object MediumPower extends Power
case object HighPower extends Power

sealed trait Bullet extends SpeedComponent {
  def power: Power
}

case object FastHighBullet extends Bullet {
  override def speed: Speed = HighSpeed
  override def power: Power = HighPower
}

case object LowHighBullet extends Bullet {
  override def speed: Speed = LowSpeed
  override def power: Power = HighPower
}

case object LowMediumBullet extends Bullet {
  override def speed: Speed = LowSpeed
  override def power: Power = HighPower
}

case object FastMediumBullet extends Bullet {
  override def speed: Speed = HighSpeed
  override def power: Power = MediumPower
}