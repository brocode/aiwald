package de.is24.aiwald

import scala.io.Source
import resource._

object MapLoader {
  val Sunflower = "🌻"
  val Tree = "🌲"
  val Person = "🙋"
  val Money = "💰"

  type GameMap = List[List[Tile]]
  def load(name: String): GameMap = {
    val lines: List[String] = managed(Source.fromInputStream(getClass.getResourceAsStream(s"/maps/$name.map"), "UTF-8")).acquireAndGet { source ⇒
      source.getLines.toList
    }

    val map = lines.map(line ⇒ asAscii(line).map(tileForChar).toList)

    if (map.length < 25 || map.map(_.size).min < 25)
      throw new RuntimeException("Illegal map size")
    else
      map
  }

  private def asAscii(line: String): String = {
    line
      .replaceAll(Sunflower, " ")
      .replaceAll(Tree, "W")
      .replaceAll(Person, "S")
      .replaceAll(Money, "C")
  }

  private def tileForChar(c: Char) = c.toString match {
    case " " ⇒ Tile.Grass
    case "W" ⇒ Tile.Tree
    case "S" ⇒ Tile.StartingArea
    case "C" ⇒ Tile.Coin
    case "B" ⇒ Tile.Bush
    case "T" ⇒ Tile.Sword
  }
}
