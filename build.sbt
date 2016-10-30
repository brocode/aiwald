import com.typesafe.sbt.SbtScalariform._
import com.typesafe.sbt.SbtScalariform.autoImport._
import sbt.Keys._
import sbt._
import scalariform.formatter.preferences._

val javaVersion = "1.8"
val encoding = "utf-8"

val enumeratumVersion: String = "1.4.9"
val slick2dVersion = "1.0.0"

val testDependencies = Seq(
  "org.specs2" %% "specs2-core" % "3.6",
  "org.specs2" %% "specs2-mock" % "3.6"
).map(_ % "test")

val appDependencies = Seq(
  "org.slf4j" % "log4j-over-slf4j" % "1.7.13",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.jsuereth" %% "scala-arm" % "1.4",
  "com.chuusai" % "shapeless_2.11" % "2.3.0",
  "com.softwaremill.macwire" %% "macros" % "2.2.5",
  "com.beachape" %% "enumeratum" % enumeratumVersion,
  "org.slick2d" % "slick2d-core" % slick2dVersion
)

resolvers ++= Seq("Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases")


lazy val root = Project(id = "AIwald", base = file("."), settings = Defaults.coreDefaultSettings ++ defaultScalariformSettings).settings(
  name := "AIwald",
  organization := "de.is24",
  shellPrompt := { state => scala.Console.CYAN + "🌲 AIwald " + scala.Console.RESET },
  scalaVersion := "2.11.8",
  libraryDependencies ++= appDependencies ++ testDependencies,
  javacOptions ++= Seq("-source", javaVersion, "-target", javaVersion, "-Xlint"),
  javaOptions ++= Seq("-Djava.library.path=./natives/"),
  scalacOptions ++= Seq("-feature", "-language:postfixOps", "-target:jvm-" + javaVersion, "-unchecked", "-deprecation", "-encoding", encoding),
  mainClass := Some("de.is24.aiwald.AIwald"),
  fork in run := true,

  /**
    * scalariform
    */
  ScalariformKeys.preferences := ScalariformKeys.preferences.value
    .setPreference(AlignArguments, true)
    .setPreference(DoubleIndentClassDeclaration, true)
    .setPreference(AlignSingleLineCaseStatements, true)
    .setPreference(SpacesAroundMultiImports, true)
    .setPreference(AlignParameters, true)
    .setPreference(RewriteArrowSymbols, true)
)
