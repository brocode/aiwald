package de.is24.aiwald

import org.newdawn.slick.{ AppGameContainer, BasicGame, GameContainer, Graphics, Image }
import MapLoader.GameMap

object AIWald extends App {
  val app: AppGameContainer = new AppGameContainer(new Game(MapLoader.load("level_1")))
  app.setDisplayMode(800, 800, false)
  app.setTargetFrameRate(60)
  app.setForceExit(false)
  app.start()
}

class Game(map: GameMap) extends BasicGame("AIwald game") {
  var grassTile: Image = null
  var brickTile: Image = null
  var coinTile: Image = null

  override def render(container: GameContainer, g: Graphics): Unit = {
    for {
      x ← 0 until 25
      y ← 0 until 25
    } {
      tileOf(x, y).foreach { tile ⇒
        g.drawImage(tile, x * 32, y * 32)
      }
    }
  }

  def tileOf(x: Int, y: Int): List[Image] = map(y)(x) match {
    case Tile.Grass ⇒ List(grassTile)
    case Tile.Wall  ⇒ List(brickTile)
    case Tile.Coin  ⇒ List(grassTile, coinTile)
  }

  override def init(container: GameContainer): Unit = {
    grassTile = new Image("grass_scaled.png")
    brickTile = new Image("wall_scaled.png")
    coinTile = new Image("coin.png")
  }

  override def update(container: GameContainer, delta: Int): Unit = {
  }
}
