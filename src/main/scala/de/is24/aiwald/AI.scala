package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap

trait AI {
  def nextMove(map: GameMap, playerLocation: PlayerLocation): Move

  def tileInFrontOfPlayer(map: GameMap, playerLocation: PlayerLocation): Tile = {
    val (x, y): (Int, Int) = playerLocation.orientation match {
      case Orientation.North ⇒ (playerLocation.x, playerLocation.y - 1)
      case Orientation.South ⇒ (playerLocation.x, playerLocation.y + 1)
      case Orientation.West  ⇒ (playerLocation.x - 1, playerLocation.y)
      case Orientation.East  ⇒ (playerLocation.x + 1, playerLocation.y)
    }
    map(y)(x)
  }
}

class MyAI extends AI {

  def nextMove(map: GameMap, playerLocation: PlayerLocation): Move = {
    if (map(playerLocation.y)(playerLocation.x) == Tile.Coin)
      Move.PICK_UP
    else
      Move.MOVE_FORWARD
  }
}
