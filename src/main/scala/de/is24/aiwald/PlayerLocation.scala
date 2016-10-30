package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap

case class PlayerLocation(x: Int, y: Int, orientation: Orientation) {
  def moveForward(): PlayerLocation = {
    orientation match {
      case Orientation.North ⇒ this.copy(y = y - 1)
      case Orientation.East  ⇒ this.copy(x = x + 1)
      case Orientation.South ⇒ this.copy(y = y + 1)
      case Orientation.West  ⇒ this.copy(x = x - 1)
    }
  }

  def isValidLocation(map: GameMap): Boolean = {
    val tile = map(y)(x)
    PlayerLocation.ValidLocationTiles.contains(tile)
  }
}

object PlayerLocation {
  val ValidLocationTiles = Seq(Tile.Grass, Tile.Coin, Tile.StartingArea)
}
