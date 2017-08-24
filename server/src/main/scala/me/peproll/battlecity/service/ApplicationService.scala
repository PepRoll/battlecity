package me.peproll.battlecity.service

import akka.http.scaladsl.server.Route
import me.peproll.battlecity.service.routes.StaticRoute

class ApplicationService extends StaticRoute {

  def route: Route = staticRoute

}
