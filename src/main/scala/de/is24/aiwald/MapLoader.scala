package de.is24.aiwald

import scala.io.Source
import resource._

object MapLoader {

  type GameMap = List[List[Tile]]

  def load(name: String): GameMap = {
    val lines: List[String] = managed(Source.fromInputStream(getClass.getResourceAsStream(s"/maps/$name.map"))).acquireAndGet { source ⇒
      source.getLines.toList
    }

    val map = lines.map(line ⇒ line.map(tileForChar).toList)

    if (map.length < 25 || map.map(_.size).min < 25)
      throw new RuntimeException("Illegal map size")
    else
      map
  }

  private def tileForChar(c: Char) = c match {
    case ' ' ⇒ Tile.Grass
    case 'W' ⇒ Tile.Tree
    case 'S' ⇒ Tile.StartingArea
    case 'C' ⇒ Tile.Coin
  }

}
