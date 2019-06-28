import play.sbt.PlayImport.{guice, specs2, ws}
import sbt.Keys._
import sbt._

object ApplicationBuild {

  val commonSettings = Defaults.itSettings ++ Seq(
    resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
    scalaVersion := "2.12.6",
    fork in Test := true,
    fork in IntegrationTest := true,
    sourceDirectory in IntegrationTest := baseDirectory.value / "it",
    libraryDependencies ++= Seq(
      "org.mockito" % "mockito-core" % "2.18.3" % "test, it",
      "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "test, it",
      ws , specs2 % Test , guice
    ),
    javaOptions in IntegrationTest ++= Seq(
      "-Dconfig.file=conf/application.test.conf",
      "-Duser.timezone=UTC"
    )
  )
}
