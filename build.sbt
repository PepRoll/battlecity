
enablePlugins(ScalaJSPlugin, WorkbenchSplicePlugin)

organization := "me.peproll"

name := "battlecity"

version := "1.0"

scalaVersion := "2.12.2"

scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Xfatal-warnings",
    "-language:higherKinds",
    "-Yno-adapted-args",
    "-Ywarn-value-discard"
)

scalaJSUseMainModuleInitializer := true

testFrameworks += new TestFramework("utest.runner.Framework")

/* javascript dependencies */
jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "15.5.4"
    / "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.5.4"
    / "react-dom.js"
    minified "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM"
)

libraryDependencies ++= Seq(
  "com.github.japgolly.scalajs-react" %%% "core" % "1.0.0",
  "com.github.japgolly.scalajs-react" %%% "extra" % "1.0.0",
  "com.github.japgolly.scalacss" %%% "ext-react" % "0.5.1",
  "com.github.japgolly.scalacss" %% "core" % "0.5.1",
  "org.scala-js" %%% "scalajs-dom" % "0.9.2",
  "com.lihaoyi" %%% "utest" % "0.4.5" % "test"
)