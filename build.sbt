
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

lazy val commonDependencies = Seq(
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-async" % "0.9.6",
    "com.lihaoyi" %% "upickle" % "0.4.4"
  )
)

lazy val shared = (crossProject.crossType(CrossType.Pure) in file("shared"))
  .settings(commonSettings)

lazy val sharedJs = shared.js
lazy val sharedJvm = shared.jvm

lazy val client = (project in file("client"))
  .enablePlugins(ScalaJSPlugin, WorkbenchPlugin)
  .dependsOn(sharedJs)
  .settings(commonSettings)
  .settings(commonDependencies)
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
        "com.github.japgolly.scalajs-react" %%% "core" % reactVersion,
        "com.github.japgolly.scalajs-react" %%% "extra" % reactVersion,
        "com.github.japgolly.scalacss" %%% "ext-react" % scalaCssVersion,
        "com.github.japgolly.scalacss" %% "core" % scalaCssVersion,
        "com.github.julien-truffaut" %%% "monocle-core" % monocleVersion,
        "com.github.julien-truffaut" %%% "monocle-macro" % monocleVersion,
        "org.scala-js" %%% "scalajs-dom" % "0.9.3",
        "com.lihaoyi" %%% "utest" % "0.4.7" % Test
      )
    }
  )

lazy val server = (project in file("server"))
  .dependsOn(sharedJvm)
  .settings(commonSettings)
  .settings(commonDependencies)
  .settings(

    name := "server",

    resourceGenerators in Compile += Def.task {
      val f1 = (fastOptJS in Compile in client).value
      val f2 = (packageJSDependencies in Compile in client).value
      val newF1 = (resourceManaged in Compile).value / "web" / "battlecity.js"
      val newF2 = (resourceManaged in Compile).value / "web" / "deps.js"
      IO.move(f1.data, newF1)
      IO.move(f2, newF2)
      Seq(newF1, newF2)
    }.taskValue,

    watchSources ++= (watchSources in client).value,

    libraryDependencies ++= {
      val akkaHttpVersion = "10.0.9"
      val akkaVersion = "2.5.4"

      Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe.akka" %% "akka-stream" % akkaVersion,
        "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
        "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
        "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
        "org.scalatest" %% "scalatest" % "3.0.1" % Test
      )
    }
  )

lazy val root = (project in file("."))
  .aggregate(client, server)
  .settings(commonSettings)