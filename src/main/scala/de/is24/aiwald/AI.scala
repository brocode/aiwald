package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap

trait AI {
  def nextMove(map: GameMap, playerLocation: PlayerData): Move

  def tileInFrontOfPlayer(map: GameMap, playerLocation: PlayerData): Tile = {
    val (x, y) = playerLocation.coordinatesInFrontOfPlayer
    map(y)(x)
  }
}
