package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import org.specs2.mutable.Specification

class AIWaldSpec extends Specification {
  class TestAI extends AI {

    def nextMove(map: GameMap, currentPlayerLocation: PlayerLocation): Move = {
      if (map(currentPlayerLocation.y)(currentPlayerLocation.x) == Tile.Coin)
        Move.PICK_UP
      else
        Move.MOVE_FORWARD
    }
  }

  "Level 1" should {
    "be winnable by TestAI" in {
      val ai = new TestAI()
      val game = new Game(MapLoader.load("level_1"), ai)
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Coin
      game.tick()
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Coin
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Tree
      game.won must beFalse
      game.tick() // this should pick up the coin
      game.won must beTrue
      ok
    }
  }
}
