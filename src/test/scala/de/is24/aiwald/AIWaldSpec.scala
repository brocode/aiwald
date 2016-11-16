package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import org.specs2.mutable.Specification

class AIWaldSpec extends Specification {

  "Level 1" should {
    "be winnable by TestAI" in {
      val ai = new AI() {
        override def nextMove(map: GameMap, currentPlayerLocation: PlayerData): Move = {
          if (map(currentPlayerLocation.y)(currentPlayerLocation.x) == Tile.Coin)
            Move.PICK_UP
          else
            Move.MOVE_FORWARD
        }
      }
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
  "AI in test level" should {
    "be able to rotate and see items" in {
      val ai = new AI() {
        override def nextMove(map: GameMap, currentPlayerLocation: PlayerData): Move = {
          if (map(currentPlayerLocation.y)(currentPlayerLocation.x) == Tile.StartingArea)
            Move.MOVE_FORWARD
          else
            Move.ROTATE_LEFT
        }
      }
      val game = new Game(MapLoader.load("test"), ai)
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Tree
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Coin
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.StartingArea
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
      game.won must beFalse
      ok
    }

    "cut bushes" in {
      val ai = new AI() {
        override def nextMove(map: GameMap, currentPlayerLocation: PlayerData): Move = {
          val currentTile = map(currentPlayerLocation.y)(currentPlayerLocation.x)
          val (nextX, nextY) = currentPlayerLocation.coordinatesInFrontOfPlayer

          if (map(nextY)(nextX) == Tile.Bush && currentPlayerLocation.hasSword)
            Move.SLASH
          else
            currentTile match {
              case Tile.Sword ⇒ Move.PICK_UP
              case _          ⇒ Move.MOVE_FORWARD
            }
        }
      }
      val game = new Game(MapLoader.load("test2"), ai)
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Sword
      game.tick()
      game.currentPlayerLocation.hasSword must beFalse
      game.tick()
      game.currentPlayerLocation.hasSword must beTrue
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Bush
      game.tick()
      ai.tileInFrontOfPlayer(game.map, game.currentPlayerLocation) must be equalTo Tile.Grass
    }
  }
}
