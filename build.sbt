
enablePlugins(ScalaJSPlugin)

organization := "me.peproll"

name := "battlecity"

version := "1.0"

scalaVersion := "2.12.2"

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
  "org.scala-js" %%% "scalajs-dom" % "0.9.2",
  "com.lihaoyi" %%% "utest" % "0.4.5" % "test"
)