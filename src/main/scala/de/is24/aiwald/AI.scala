package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap

trait AI {
  def nextMove(map: GameMap, playerLocation: PlayerLocation): Move
}

class MyAI extends AI {

  def nextMove(map: GameMap, playerLocation: PlayerLocation): Move = {
    if (map(playerLocation.y)(playerLocation.x) == Tile.Coin)
      Move.PICK_UP
    else
      Move.MOVE_FORWARD
  }
}
