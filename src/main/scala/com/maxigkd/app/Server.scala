package com.maxigkd.app


import com.maxigkd.app.controller.HelloWorldController
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import org.http4s.server.blaze._
import org.http4s.server.middleware.Logger
import pureconfig.generic.auto._


final case class ServerConfig(host: String, port: Int)

object MainApp extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val serverConfig = pureconfig.loadConfigOrThrow[ServerConfig]("dockerized-scala-app.server-config")

    val server = Server.apply(serverConfig, new HelloWorldController)
    server.run()
  }

  case class Server(serverConfig: ServerConfig, controller: HelloWorldController) {

    def run(): IO[ExitCode] = {

      BlazeServerBuilder[IO]
        .bindHttp(serverConfig.port, serverConfig.host)
        .withHttpApp(Logger.httpApp(logHeaders = true, logBody = true)(controller.register()))
        .withoutBanner
        .serve
        .compile
        .drain
        .as(ExitCode.Success)
    }

  }

}