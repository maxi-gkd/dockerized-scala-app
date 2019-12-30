# Dockerized-scala-app

A simple REST API with only one endpoint /hello/${name} that returns Hello, <name>.

The objective of this project is to show how to link docker with sbt in a Scala app using sbt-native-packager.

## Getting Started
- git checkout master
- sbt clean compile
- sbt docker:publishLocal
- run with: docker run --rm -p 8081:8081 dockerized-scala-app
- Test in: http://localhost:8081/hello/<name>

### Prerequisites

- jdk
- sbt
- docker

## Built with

- [sbt-native-packager](https://www.scala-sbt.org/sbt-native-packager/) - A plugin that allows you to generate native packages from your projects.
- [macwire](https://github.com/softwaremill/macwire/) - Used for dependency injection.
- [Http4s](https://github.com/http4s/http4s/) - Used as a web server.
 