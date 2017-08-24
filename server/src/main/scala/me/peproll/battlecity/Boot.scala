package me.peproll.battlecity

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import me.peproll.battlecity.service.ApplicationService

import scala.util.{Failure, Success}

object Boot extends App {
  implicit val system = ActorSystem()
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val service = new ApplicationService
  val config = ConfigFactory.load()

  Http().bindAndHandle(service.route, config.getString("app.host"), config.getInt("app.port")).onComplete {
    case Success(binding) ⇒
      val localAddress = binding.localAddress
      println(s"Server is listening on ${localAddress.getHostName}:${localAddress.getPort}")
    case Failure(e) ⇒
      println(s"Binding failed with ${e.getMessage}")
      system.terminate()
  }
}
