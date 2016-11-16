package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import Orientation._

case class PlayerData(x: Int, y: Int, orientation: Orientation, hasSword: Boolean = false) {
  def moveForward(): PlayerData = {
    orientation match {
      case North ⇒ this.copy(y = y - 1)
      case East  ⇒ this.copy(x = x + 1)
      case South ⇒ this.copy(y = y + 1)
      case West  ⇒ this.copy(x = x - 1)
    }
  }

  def isValidLocation(map: GameMap): Boolean = {
    val tile = map(y)(x)
    PlayerData.ValidLocationTiles.contains(tile)
  }

  def coordinatesInFrontOfPlayer: (Int, Int) = {
    orientation match {
      case Orientation.North ⇒ (x, y - 1)
      case Orientation.South ⇒ (x, y + 1)
      case Orientation.West  ⇒ (x - 1, y)
      case Orientation.East  ⇒ (x + 1, y)
    }
  }

  def rotateLeft(): PlayerData = orientation match {
    case North ⇒ this.copy(orientation = West)
    case East  ⇒ this.copy(orientation = North)
    case South ⇒ this.copy(orientation = East)
    case West  ⇒ this.copy(orientation = South)
  }

  def rotateRight(): PlayerData = orientation match {
    case North ⇒ this.copy(orientation = East)
    case East  ⇒ this.copy(orientation = South)
    case South ⇒ this.copy(orientation = West)
    case West  ⇒ this.copy(orientation = North)
  }
}

object PlayerData {
  val ValidLocationTiles = Seq(Tile.Grass, Tile.Coin, Tile.StartingArea, Tile.Sword)
}
