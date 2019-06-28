name := "e-commerce"
version := "1.0"
organization := "com.example"

lazy val common =  project.in(file("modules/common"))

lazy val payment = Project("payment", file("modules/payment"))
  .enablePlugins(PlayScala)
  .settings(ApplicationBuild.commonSettings)
  .dependsOn(common)
  .aggregate(common)
  .configs(IntegrationTest)

lazy val search = Project("search", file("modules/search"))
  .enablePlugins(PlayScala)
  .settings(ApplicationBuild.commonSettings)
  .dependsOn(common)
  .aggregate(common)
  .configs(IntegrationTest)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(ApplicationBuild.commonSettings)
  .dependsOn(payment, search)
  .aggregate(payment, search)
  .configs(IntegrationTest)