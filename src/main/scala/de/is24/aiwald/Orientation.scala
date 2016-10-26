package de.is24.aiwald

import enumeratum._

sealed trait Orientation extends EnumEntry

object Orientation extends Enum[Orientation] {
  override def values = findValues

  case object North extends Orientation
  case object East extends Orientation
  case object South extends Orientation
  case object West extends Orientation
}
