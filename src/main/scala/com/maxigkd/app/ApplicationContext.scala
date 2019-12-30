package com.maxigkd.app

import cats.data.Kleisli
import cats.effect.IO
import com.maxigkd.app.MainApp.Server
import com.maxigkd.app.route.HelloWorldRoute
import com.softwaremill.macwire._
import org.http4s.{Request, Response}
import pureconfig.generic.auto._

trait ApplicationContext {
  val serverConfig: ServerConfig = pureconfig.loadConfigOrThrow[ServerConfig]("dockerized-scala-app.server-config")
  val routes: Kleisli[IO, Request[IO], Response[IO]] = wire[HelloWorldRoute].routes
  val server: Server = wire[Server]
}
