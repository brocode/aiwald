package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import Orientation._

case class PlayerData(x: Int, y: Int, orientation: Orientation, hasSword: Boolean = false) {
  def moveForward(): PlayerData = {
    val (x, y) = coordinatesInFrontOfPlayer
    this.copy(x = x, y = y)
  }

  def isValidLocation(map: GameMap): Boolean = {
    val tile = map(y)(x)
    PlayerData.ValidLocationTiles.contains(tile)
  }

  def coordinatesInFrontOfPlayer: (Int, Int) = {
    coordinatesInOrientation(orientation)
  }

  def coordinatesLeftOfPlayer: (Int, Int) = {
    coordinatesInOrientation(leftOrientation)
  }

  def coordinatesRightOfPlayer: (Int, Int) = {
    coordinatesInOrientation(rightOrientation)
  }

  private def coordinatesInOrientation(orientation: Orientation) = {
    orientation match {
      case Orientation.North ⇒ (x, y - 1)
      case Orientation.South ⇒ (x, y + 1)
      case Orientation.West  ⇒ (x - 1, y)
      case Orientation.East  ⇒ (x + 1, y)
    }
  }

  private def leftOrientation: Orientation = {
    orientation match {
      case North ⇒ West
      case East  ⇒ North
      case South ⇒ East
      case West  ⇒ South
    }
  }

  private def rightOrientation: Orientation = {
    orientation match {
      case North ⇒ East
      case East  ⇒ South
      case South ⇒ West
      case West  ⇒ North
    }
  }

  def rotateLeft(): PlayerData =
    this.copy(orientation = leftOrientation)

  def rotateRight(): PlayerData =
    this.copy(orientation = rightOrientation)
}

object PlayerData {
  val ValidLocationTiles = Seq(Tile.Grass, Tile.Coin, Tile.StartingArea, Tile.Sword)

}
