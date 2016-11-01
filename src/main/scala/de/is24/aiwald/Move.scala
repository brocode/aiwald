package de.is24.aiwald

import enumeratum._

sealed trait Move extends EnumEntry

object Move extends Enum[Move] {

  case object MOVE_FORWARD extends Move
  case object ROTATE_LEFT extends Move
  case object ROTATE_RIGTH extends Move
  case object PICK_UP extends Move
  case object DO_NOTHING extends Move

  override def values = findValues
}
