package de.is24.aiwald

import enumeratum._

sealed abstract class Orientation(override val entryName: String) extends EnumEntry

object Orientation extends Enum[Orientation] {
  override def values = findValues

  case object North extends Orientation("north")
  case object East extends Orientation("east")
  case object South extends Orientation("south")
  case object West extends Orientation("west")
}
