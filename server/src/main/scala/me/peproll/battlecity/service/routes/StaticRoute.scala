package me.peproll.battlecity.service.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait StaticRoute {

  def staticRoute: Route =
    (get & pathPrefix("web")){
      (pathEndOrSingleSlash & redirectToTrailingSlashIfMissing(StatusCodes.TemporaryRedirect)) {
        getFromResource("web/index.html")
      } ~  {
        getFromResourceDirectory("web")
      }
    }
}
