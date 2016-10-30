package de.is24.aiwald

import de.is24.aiwald.MapLoader.GameMap
import enumeratum._

sealed trait Move extends EnumEntry

object Move extends Enum[Move] {

  case object MOVE_FORWARD extends Move
  case object ROTATE_LEFT extends Move
  case object ROTATE_RIGTH extends Move
  case object PICK_UP extends Move

  override def values = findValues
}

class AI {

  def nextMove(map: GameMap, playerLocation: PlayerLocation): Move = {
    Move.MOVE_FORWARD
  }
}
