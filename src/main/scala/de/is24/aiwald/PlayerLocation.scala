package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import Orientation._

case class PlayerLocation(x: Int, y: Int, orientation: Orientation) {
  def moveForward(): PlayerLocation = {
    orientation match {
      case North ⇒ this.copy(y = y - 1)
      case East  ⇒ this.copy(x = x + 1)
      case South ⇒ this.copy(y = y + 1)
      case West  ⇒ this.copy(x = x - 1)
    }
  }

  def isValidLocation(map: GameMap): Boolean = {
    val tile = map(y)(x)
    PlayerLocation.ValidLocationTiles.contains(tile)
  }

  def rotateLeft(): PlayerLocation = orientation match {
    case North ⇒ this.copy(orientation = West)
    case East  ⇒ this.copy(orientation = North)
    case South ⇒ this.copy(orientation = East)
    case West  ⇒ this.copy(orientation = South)
  }

  def rotateRight(): PlayerLocation = orientation match {
    case North ⇒ this.copy(orientation = East)
    case East  ⇒ this.copy(orientation = South)
    case South ⇒ this.copy(orientation = West)
    case West  ⇒ this.copy(orientation = North)
  }
}

object PlayerLocation {
  val ValidLocationTiles = Seq(Tile.Grass, Tile.Coin, Tile.StartingArea)
}
