package de.is24.aiwald

import enumeratum._
import java.nio.file.Paths

class AvatarCollection(avatarName: AvatarName) {
  import AvatarCollection._
  def facing(orientation: Orientation): String =
    Paths.get(avatarDirectory, avatarName.entryName, orientation.entryName).toString + ".png"

}

object AvatarCollection {
  val avatarDirectory = "players"
}

sealed abstract class AvatarName(override val entryName: String) extends EnumEntry

object AvatarName extends Enum[AvatarName] {

  case object LIDIA extends AvatarName("lidia")
  case object TROLL extends AvatarName("troll")

  override def values = findValues
}
