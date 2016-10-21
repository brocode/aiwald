package de.is24.aiwald

import org.newdawn.slick.{ AppGameContainer, BasicGame, GameContainer, Graphics, Image }
import enumeratum._

object AIWald extends App {
  val app: AppGameContainer = new AppGameContainer(new Game())
  app.setDisplayMode(800, 800, false)
  app.setTargetFrameRate(60)
  app.setForceExit(false)
  app.start()
}

sealed trait Tile extends EnumEntry

object Tile extends Enum[Tile] {
  override val values = findValues

  case object Grass extends Tile
  case object Wall extends Tile
}

class Game extends BasicGame("AIwald game") {
  var grassTile: Image = null
  var brickTile: Image = null
  val map: Array[Array[Tile]] = Array.tabulate(50, 50)((x, y) ⇒ if ((x + y) % 2 == 0) Tile.Grass else Tile.Wall)

  override def render(container: GameContainer, g: Graphics): Unit = {
    for {
      x ← 0 until 50
      y ← 0 until 50
    } {
      g.drawImage(tileOf(x, y), x * 16, y * 16)
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
