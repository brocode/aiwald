package de.is24.aiwald

import enumeratum._
sealed trait Tile extends EnumEntry

object Tile extends Enum[Tile] {
  override val values = findValues

  case object Grass extends Tile
  case object Wall extends Tile
}
