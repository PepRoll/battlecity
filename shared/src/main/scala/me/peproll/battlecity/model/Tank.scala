package me.peproll.battlecity.model

import me.peproll.battlecity.model.component._

trait Tank extends Entity
  with Directinable
  with BulletComponent {
  def tankTrack: TankTrack
}

case class PlayerTank(position: Coordinates,
                      direction: Direction,
                      tankTrack: TankTrack,
                      shield: Boolean,
                      rank: PlayerRank) extends Tank {
  override def bullet: BulletType = rank.bullet
  override def bulletCount: Int = rank.bulletCount
}

case class EnemyTank(position: Coordinates,
                     direction: Direction,
                     tankTrack: TankTrack,
                     withBonus: Boolean,
                     rank: EnemyType) extends Tank {
  override def bullet: BulletType = rank.bullet
  override def bulletCount: Int = rank.bulletCount
}


sealed trait TankType extends SpeedComponent
  with BulletComponent
  with ArmorComponent

sealed trait PlayerRank extends TankType
case object Solder extends PlayerRank {
  override def speed: Speed = LowSpeed
  override def bullet: BulletType = LowMediumBulletType
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object Major extends PlayerRank {
  override def speed: Speed = MediumSpeed
  override def bullet: BulletType = FastHighBulletType
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object Ltc extends PlayerRank {
  override def speed: Speed = HighSpeed
  override def bullet: BulletType = FastHighBulletType
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object Colonel extends PlayerRank {
  override def speed: Speed = HighSpeed
  override def bullet: BulletType = FastHighBulletType
  override def bulletCount: Int = 2
  override def armor: Armor = LowArmor
}

sealed trait EnemyType extends TankType
case object BasicType extends EnemyType {
  override def speed: Speed = MediumSpeed
  override def bullet: BulletType = LowMediumBulletType
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case class ArmorType(armor: Armor = HeavyArmor) extends EnemyType {
  override def speed: Speed = LowSpeed
  override def bullet: BulletType = FastHighBulletType
  override def bulletCount: Int = 1
}
case object FastType extends EnemyType {
  override def speed: Speed = HighSpeed
  override def bullet: BulletType = FastMediumBulletType
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}
case object PowerType extends EnemyType {
  override def speed: Speed = MediumSpeed
  override def bullet: BulletType = LowHighBulletType
  override def bulletCount: Int = 1
  override def armor: Armor = LowArmor
}

sealed trait TankTrack {
  def next: TankTrack
}

case object FirstPosition extends TankTrack {
  override def next: TankTrack = SecondPosition
}

case object SecondPosition extends TankTrack {
  override def next: TankTrack = FirstPosition
}