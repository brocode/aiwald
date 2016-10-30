package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import org.newdawn.slick.GameContainer
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class AIWaldSpec extends Specification with Mockito {
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
      val gameContainer = smartMock[GameContainer]
      for (x ← 1 to 13) {
        game.update(null, 10000)
        game.won must beFalse
      }
      game.update(null, 10000) // this should pick up the coin
      game.won must beTrue
      ok
    }
  }
}
