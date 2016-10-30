package de.is24.aiwald

import org.newdawn.slick.{ AppGameContainer, BasicGame, GameContainer, Graphics, Image }
import MapLoader.GameMap

object AIWald extends App {
  val app: AppGameContainer = new AppGameContainer(new Game(MapLoader.load("level_1")))
  app.setDisplayMode(800, 800, false)
  app.setTargetFrameRate(60)
  app.setTitle("AIWald")
  app.setMinimumLogicUpdateInterval(50)
  app.setMaximumLogicUpdateInterval(50)
  app.setForceExit(false)
  app.start()
}

case class PlayerLocation(x: Int, y: Int, orientation: Orientation) {
  def moveForward(): PlayerLocation = {
    orientation match {
      case Orientation.North ⇒ this.copy(y = y - 1)
      case Orientation.East  ⇒ this.copy(x = x + 1)
      case Orientation.South ⇒ this.copy(y = y + 1)
      case Orientation.West  ⇒ this.copy(x = x - 1)
    }
  }
}

class Game(map: GameMap) extends BasicGame("AIwald game") {
  val ai = new AI()
  var grassTile: Image = null
  var treeTile: Image = null
  var coinTile: Image = null
  var towerTile: Image = null
  var playerNorth: Image = null
  var playerEast: Image = null
  var playerSouth: Image = null
  var playerWest: Image = null
  var playerLocation = getStartingPlayerLocation(map)
  var timeSinceLastUpdate: Long = 0L

  override def render(container: GameContainer, g: Graphics): Unit = {
    implicit val graphics = g
    for {
      x ← 0 until 25
      y ← 0 until 25
    } {
      tileOf(x, y).foreach { tile ⇒
        drawAt(x, y, tile)
      }
      drawAt(playerLocation.x, playerLocation.y, playerImage(playerLocation.orientation))
    }
  }

  private def drawAt(x: Int, y: Int, image: Image)(implicit g: Graphics) = {
    g.drawImage(image, x * 32, y * 32)
  }

  def tileOf(x: Int, y: Int): List[Image] = map(y)(x) match {
    case Tile.Grass        ⇒ List(grassTile)
    case Tile.Tree         ⇒ List(grassTile, treeTile)
    case Tile.Coin         ⇒ List(grassTile, coinTile)
    case Tile.StartingArea ⇒ List(grassTile, towerTile)
  }

  override def init(container: GameContainer): Unit = {
    grassTile = new Image("grass_scaled.png")
    treeTile = new Image("tree.png")
    coinTile = new Image("coin.png")
    towerTile = new Image("tower.png")
    playerNorth = new Image("player_north.png")
    playerEast = new Image("player_east.png")
    playerSouth = new Image("player_south.png")
    playerWest = new Image("player_west.png")
  }

  def playerImage(orientation: Orientation) = orientation match {
    case Orientation.North ⇒ playerNorth
    case Orientation.East  ⇒ playerEast
    case Orientation.South ⇒ playerSouth
    case Orientation.West  ⇒ playerWest
  }

  override def update(container: GameContainer, delta: Int): Unit = {
    timeSinceLastUpdate = timeSinceLastUpdate + delta
    if (timeSinceLastUpdate >= 500) {
      tick()
      timeSinceLastUpdate = 0
    }
  }

  def tick(): Unit = {
    val move = ai.nextMove(map, playerLocation)
    move match {
      case Move.MOVE_FORWARD ⇒
        moveForward()
    }
  }

  private def moveForward() {
    val newLocation = playerLocation.moveForward()
    if (isValidLocation(newLocation))
      playerLocation = newLocation
    else
      println("dead end")
  }

  private def isValidLocation(playerLocation: PlayerLocation): Boolean = {
    val validTiles = Seq(Tile.Grass, Tile.Coin, Tile.StartingArea)
    val tile = map(playerLocation.y)(playerLocation.x)
    validTiles.contains(tile)
  }

  def getStartingPlayerLocation(map: GameMap): PlayerLocation = {
    val (row, y) = map.zipWithIndex.filter {
      case (row, index) ⇒
        row.exists(tile ⇒ tile == Tile.StartingArea)
    }.head
    val x = row.zipWithIndex.filter(_._1 == Tile.StartingArea).head._2
    PlayerLocation(x, y, Orientation.South)
  }
}
