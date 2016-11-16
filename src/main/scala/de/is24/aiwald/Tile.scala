package de.is24.aiwald

import enumeratum._
sealed trait Tile extends EnumEntry

object Tile extends Enum[Tile] {
  override val values = findValues

  case object Grass extends Tile
  case object Tree extends Tile
  case object Coin extends Tile
  case object StartingArea extends Tile
  case object Bush extends Tile
  case object Sword extends Tile
}
