package me.peproll.battlecity.model

import me.peproll.battlecity.model.component.{BulletType, Direction}

case class Bullet(position: Coordinates,
                  direction: Direction,
                  bulletType: BulletType) extends Entity
