package me.peproll.battlecity.model

import me.peproll.battlecity.model.component._

sealed trait Tank extends Entity {
  def tankTrack: TankTrack
}

case class PlayerTank(position: Coordinates,
                      direction: Direction,
                      tankTrack: TankTrack,
                      shield: Boolean,
                      rank: PlayerRank) extends Tank

case class EnemyTank(position: Coordinates,
                     direction: Direction,
                     tankTrack: TankTrack,
                     withBonus: Boolean,
                     rank: EnemyType) extends Tank


sealed trait TankType extends SpeedComponent
  with BulletComponent
  with ArmorComponent

sealed trait PlayerRank extends TankType
case object Solder extends PlayerRank {
  override def speed: Speed = LowSpeed
  override def bullet: Bullet = LowMediumBullet
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object Major extends PlayerRank {
  override def speed: Speed = MediumSpeed
  override def bullet: Bullet = FastHighBullet
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object Ltc extends PlayerRank {
  override def speed: Speed = HighSpeed
  override def bullet: Bullet = FastHighBullet
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object Colonel extends PlayerRank {
  override def speed: Speed = HighSpeed
  override def bullet: Bullet = FastHighBullet
  override def bulletCount: Int = 2
  override def armor: Armor = LowArmor
}

sealed trait EnemyType extends TankType
case object BasicType extends EnemyType {
  override def speed: Speed = MediumSpeed
  override def bullet: Bullet = LowMediumBullet
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case class ArmorType(armor: Armor = HeavyArmor) extends EnemyType {
  override def speed: Speed = LowSpeed
  override def bullet: Bullet = FastHighBullet
  override def bulletCount: Int = 1
}
case object FastType extends EnemyType {
  override def speed: Speed = HighSpeed
  override def bullet: Bullet = FastMediumBullet
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object PowerType extends EnemyType {
  override def speed: Speed = MediumSpeed
  override def bullet: Bullet = LowHighBullet
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}

sealed trait TankTrack {
  def nextPosition: TankTrack
}

case object FirstPosition extends TankTrack {
  override def nextPosition: TankTrack = SecondPosition
}

case object SecondPosition extends TankTrack {
  override def nextPosition: TankTrack = FirstPosition
}