package com.maxigkd.app

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.server.blaze._
import org.http4s.server.middleware.Logger


case class ServerConfig(host: String, port: Int)

object MainApp extends IOApp with ApplicationContext {
  def run(args: List[String]): IO[ExitCode] = {
       server.run()
  }

  case class Server() {
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