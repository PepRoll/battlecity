
lazy val commonSettings = Seq(
  organization := "me.peproll",

  scalaVersion := "2.12.3",

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
)

lazy val client = (project in file("client"))
  .enablePlugins(ScalaJSPlugin, WorkbenchPlugin)
  .settings(commonSettings)
  .settings(

    name := "client",

    scalaJSUseMainModuleInitializer := true,

    testFrameworks += new TestFramework("utest.runner.Framework"),

    /* javascript dependencies */
    jsDependencies ++= {

      val reactVersion = "15.6.1"

      Seq(
        "org.webjars.bower" % "react" % reactVersion
          / "react-with-addons.js"
          minified "react-with-addons.min.js"
          commonJSName "React",

        "org.webjars.bower" % "react" % reactVersion
          / "react-dom.js"
          minified "react-dom.min.js"
          dependsOn "react-with-addons.js"
          commonJSName "ReactDOM"
      )
    },

    libraryDependencies ++= {

      val reactVersion = "1.1.0"
      val scalaCssVersion = "0.5.3"
      val monocleVersion = "1.4.0"

      Seq(
        "org.scala-lang.modules" %% "scala-async" % "0.9.6",
        "com.github.japgolly.scalajs-react" %%% "core" % reactVersion,
        "com.github.japgolly.scalajs-react" %%% "extra" % reactVersion,
        "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion,
        "com.github.japgolly.scalacss" %% "core" % scalaCssVersion,
        "com.github.julien-truffaut" %%% "monocle-core" % monocleVersion,
        "com.github.julien-truffaut" %%% "monocle-macro" % monocleVersion,
        "org.scala-js" %%% "scalajs-dom" % "0.9.3",
        "com.lihaoyi" %%% "utest" % "0.4.7" % "test"
      )
    }
  )

lazy val root = (project in file("."))
  .aggregate(client)
  .settings(commonSettings)