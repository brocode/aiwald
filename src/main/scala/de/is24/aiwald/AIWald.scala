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

  override def render(container: GameContainer, g: Graphics): Unit = {
    for {
      x ← 0 until 25
      y ← 0 until 25
    } {
      g.drawImage(tileOf(x, y), x * 32, y * 32)
    }
  }

  def tileOf(x: Int, y: Int): Image = map(x)(y) match {
    case Tile.Grass ⇒ grassTile
    case Tile.Wall  ⇒ brickTile
  }

  override def init(container: GameContainer): Unit = {
    grassTile = new Image("grass_scaled.png")
    brickTile = new Image("wall_scaled.png")
  }

  override def update(container: GameContainer, delta: Int): Unit = {
  }
}
