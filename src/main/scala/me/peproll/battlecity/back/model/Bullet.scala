package me.peproll.battlecity.back.model

import me.peproll.battlecity.back.model.component.{BulletType, Direction}

case class Bullet(position: Coordinates,
                  direction: Direction,
                  bulletType: BulletType) extends Entity
