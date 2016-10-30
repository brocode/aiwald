package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import org.specs2.mutable.Specification

class AIWaldSpec extends Specification {
  class TestAI extends AI {

    def nextMove(map: GameMap, playerLocation: PlayerLocation): Move = {
      if (map(playerLocation.y)(playerLocation.x) == Tile.Coin)
        Move.PICK_UP
      else
        Move.MOVE_FORWARD
    }
  }

  "Level 1" should {
    "be winnable by TestAI in 14 turns" in {
      val game = new Game(MapLoader.load("level_1"), new TestAI())
      for (x ← 1 to 13) {
        game.tick()
        game.won must beFalse
      }
      game.tick() // this should pick up the coin
      game.won must beTrue
      ok
    }
  }
}
