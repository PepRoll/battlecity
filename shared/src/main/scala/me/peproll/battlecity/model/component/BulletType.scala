package me.peproll.battlecity.model.component

trait BulletComponent {
  def bullet: BulletType
  def bulletCount: Int
}

sealed trait Power
case object MediumPower extends Power
case object HighPower extends Power

sealed trait BulletType extends SpeedComponent {
  def power: Power
}

case object FastHighBulletType extends BulletType {
  override def speed: Speed = HighSpeed
  override def power: Power = HighPower
}

case object LowHighBulletType extends BulletType {
  override def speed: Speed = LowSpeed
  override def power: Power = HighPower
}

case object LowMediumBulletType extends BulletType {
  override def speed: Speed = LowSpeed
  override def power: Power = HighPower
}

case object FastMediumBulletType extends BulletType {
  override def speed: Speed = HighSpeed
  override def power: Power = MediumPower
}