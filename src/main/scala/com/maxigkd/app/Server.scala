package com.maxigkd.app


import cats.data.Kleisli
import com.maxigkd.app.controller.HelloWorldController
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.{Request, Response}
import org.http4s.server.blaze._
import org.http4s.server.middleware.Logger
import pureconfig.generic.auto._


case class ServerConfig(host: String, port: Int)

object MainApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val serverConfig = pureconfig.loadConfigOrThrow[ServerConfig]("dockerized-scala-app.server-config")
    val server = Server(serverConfig, new HelloWorldController().register())
    server.run()
  }

  case class Server(serverConfig: ServerConfig, routes: Kleisli[IO, Request[IO], Response[IO]]) {
    def run(): IO[ExitCode] = {
      BlazeServerBuilder[IO]
        .bindHttp(serverConfig.port, serverConfig.host)
        .withHttpApp(Logger.httpApp(logHeaders = true, logBody = true)(routes))
        .withoutBanner
        .serve
        .compile
        .drain
        .as(ExitCode.Success)
    }
  }

}