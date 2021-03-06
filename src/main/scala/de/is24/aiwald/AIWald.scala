package de.is24.aiwald

import java.awt.Font
import org.newdawn.slick.{ AppGameContainer, BasicGame, Color, GameContainer, Graphics, Image, TrueTypeFont }
import MapLoader.GameMap

object AIWald extends App {
  val levelName: String = args.toList.headOption.getOrElse(throw new IllegalArgumentException("No level name specified"))
  val app: AppGameContainer = new AppGameContainer(new Game(MapLoader.load(levelName)))
  app.setDisplayMode(800, 832, false)
  app.setTargetFrameRate(60)
  app.setTitle("AIWald")
  app.setMinimumLogicUpdateInterval(50)
  app.setMaximumLogicUpdateInterval(50)
  app.setForceExit(true)
  app.setShowFPS(false)
  app.start()
}

class Game(var map: GameMap, ai: AI = new MyAI()) extends BasicGame("AIwald game") {
  val avatar = new AvatarCollection(AvatarName.LIDIA)
  private var grassTile: Image = null
  private var treeTile: Image = null
  private var coinTile: Image = null
  private var towerTile: Image = null
  private var playerNorth: Image = null
  private var playerEast: Image = null
  private var playerSouth: Image = null
  private var playerWest: Image = null
  private var wonImage: Image = null
  private var pavingOverlayTile: Image = null
  private var playerLocation = getStartingPlayerLocation(map)
  private var timeSinceLastUpdate: Long = 0L
  private var victoryFont: TrueTypeFont = null
  private var victoryRawFont: Font = null
  private var statusFont: TrueTypeFont = null
  private var statusRawFont: Font = null
  private var bushTile: Image = null
  private var swordTile: Image = null

  def currentPlayerLocation: PlayerData = playerLocation
  override def render(container: GameContainer, g: Graphics): Unit = {
    implicit val graphics = g
    for {
      x ← 0 until 25
      y ← 0 until 25
    } {
      tileOf(x, y).foreach { tile ⇒
        drawAt(x, y, tile)
      }
      drawAt(playerLocation.x, playerLocation.y, playerImage(playerLocation.orientation), yOffset = -5)
    }
    renderStatusBar(g)
    if (won) {
      finishGame(g)
    }
  }

  private def renderStatusBar(g: Graphics): Unit = {
    g.setColor(Color.lightGray)
    g.fillRect(0, 800, 800, 32)
    g.setFont(statusFont)
    g.setColor(Color.black)
    g.drawString("Items:", 0, 805)
    if (playerLocation.hasSword)
      g.drawImage(swordTile, 70, 800)
  }

  private def finishGame(g: Graphics): Unit = {
    g.drawImage(wonImage, 600, 30)
    g.setColor(Color.yellow)
    g.setFont(victoryFont)
    g.drawString("Victory!", 500, 30)
  }

  private def drawAt(x: Int, y: Int, image: Image, yOffset: Int = 0)(implicit g: Graphics) = {
    g.drawImage(image, x.toFloat * 32, y.toFloat * 32 + yOffset)
  }

  def tileOf(x: Int, y: Int): List[Image] = map(y)(x) match {
    case Tile.Grass        ⇒ List(grassTile, pavingOverlayTile)
    case Tile.Tree         ⇒ List(grassTile, treeTile)
    case Tile.Coin         ⇒ List(grassTile, coinTile)
    case Tile.StartingArea ⇒ List(grassTile, towerTile)
    case Tile.Bush         ⇒ List(grassTile, bushTile)
    case Tile.Sword        ⇒ List(grassTile, pavingOverlayTile, swordTile)
  }

  override def init(container: GameContainer): Unit = {
    pavingOverlayTile = new Image("paving.png")
    grassTile = new Image("grass_scaled.png")
    treeTile = new Image("tree.png")
    coinTile = new Image("coin.png")
    towerTile = new Image("tower.png")
    playerNorth = new Image(avatar.facing(Orientation.North))
    playerEast = new Image(avatar.facing(Orientation.East))
    playerSouth = new Image(avatar.facing(Orientation.South))
    playerWest = new Image(avatar.facing(Orientation.West))
    wonImage = new Image("won.png")
    victoryRawFont = new Font("Time New Roman", Font.BOLD, 35)
    victoryFont = new TrueTypeFont(victoryRawFont, true)
    statusRawFont = new Font("Time New Roman", Font.PLAIN, 20)
    statusFont = new TrueTypeFont(statusRawFont, true)
    bushTile = new Image("bush.png")
    swordTile = new Image("sword.png")
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
    if (!won) {
      val move = ai.nextMove(map, playerLocation)
      println(s"Execute move $move")
      move match {
        case Move.MOVE_FORWARD ⇒
          moveForward()
        case Move.ROTATE_LEFT ⇒
          playerLocation = playerLocation.rotateLeft()
        case Move.ROTATE_RIGHT ⇒
          playerLocation = playerLocation.rotateRight()
        case Move.PICK_UP ⇒
          pickUp()
        case Move.SLASH ⇒
          slashForward()
        case Move.DO_NOTHING ⇒
          ()
      }
    }
  }

  def won: Boolean = !coinsLeft

  private def slashForward(): Unit = {
    val (x, y) = playerLocation.coordinatesInFrontOfPlayer
    val forwardTile = map(y)(x)
    forwardTile match {
      case Tile.Bush if playerLocation.hasSword ⇒
        setTile(y, x)(Tile.Grass)
      case _ ⇒
        println(s"Can't slash at $forwardTile")
    }
  }

  private def setTile(y: Int, x: Int)(tile: Tile): Unit =
    map = map.updated(y, map(y).updated(x, tile))

  private def pickUp(): Unit = {

    def setCurrentTile(tile: Tile): Unit =
      setTile(playerLocation.y, playerLocation.x)(tile)

    currentTile match {
      case Tile.Coin ⇒
        setCurrentTile(Tile.Grass)
      case Tile.Sword ⇒
        playerLocation = playerLocation.copy(hasSword = true)
        setCurrentTile(Tile.Grass)
      case _ ⇒ println(s"Can't pick up ${currentTile}")
    }
  }

  private def currentTile = map(playerLocation.y)(playerLocation.x)

  private def moveForward() = {
    val newLocation = playerLocation.moveForward()
    if (newLocation.isValidLocation(map))
      playerLocation = newLocation
    else
      println("dead end")
  }

  private def coinsLeft: Boolean = map.flatten.toList.contains(Tile.Coin)

  def getStartingPlayerLocation(map: GameMap): PlayerData = {
    val (row, y) = map.zipWithIndex.filter {
      case (row, index) ⇒
        row.exists(tile ⇒ tile == Tile.StartingArea)
    }.head
    val x = row.zipWithIndex.filter(_._1 == Tile.StartingArea).head._2
    PlayerData(x, y, Orientation.South)
  }
}
