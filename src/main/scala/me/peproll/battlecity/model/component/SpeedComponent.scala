package me.peproll.battlecity.model.component

trait SpeedComponent {
  def speed: Speed
}

sealed trait Speed {
  def value: Int
}
case object LowSpeed extends Speed {
  override def value: Int = 1
}
case object MediumSpeed extends Speed {
  override def value: Int = 2
}
case object HighSpeed extends Speed {
  override def value: Int = 3
}




