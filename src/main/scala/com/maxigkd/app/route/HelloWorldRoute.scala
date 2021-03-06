package com.maxigkd.app.route

import cats.data.Kleisli
import cats.effect.IO
import org.http4s.{HttpRoutes, Request, Response}
import org.http4s.dsl.io._
import org.http4s.implicits._


class HelloWorldRoute {
  val routes:  Kleisli[IO, Request[IO], Response[IO]] = {
    HttpRoutes.of[IO] {
      case GET -> Root / "hello" / name => get(name)
    }.orNotFound
  }

  private def get(name: String) = {
    Ok(s"Hello, $name.")
  }
}
