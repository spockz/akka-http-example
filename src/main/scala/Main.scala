import akka.actor.ActorSystem

import akka.pattern.ask

import akka.io.IO
import akka.http.Http

import akka.stream.scaladsl.Flow
import akka.stream.{ MaterializerSettings, FlowMaterializer }
import akka.util.Timeout

import concurrent.duration._

object Main extends App {
  implicit val system = ActorSystem()
  import system.dispatcher
  val materializer = FlowMaterializer(MaterializerSettings())
  implicit val askTimeout: Timeout = 500.millis

  val bindingFuture = IO(Http) ? Http.Bind(interface = "0.0.0.0", port = 8080)
  bindingFuture foreach {
    case Http.ServerBinding(localAddress, connectionStream) ⇒
      Flow(connectionStream).foreach {
        case Http.IncomingConnection(remoteAddress, requestProducer, responseConsumer) ⇒
          //          println("Accepted new connection from " + remoteAddress)
          Flow(requestProducer).map(Routes.requestHandler).produceTo(materializer, responseConsumer)
      }.consume(materializer)
  }
}

object Routes {
  import akka.http.model._
  import HttpMethods._

  val requestHandler: HttpRequest ⇒ HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) ⇒
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/html`,
          "<html><body>Hello world!</body></html>"))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _)  ⇒ HttpResponse(entity = "PONG!")
    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) ⇒ sys.error("BOOM!")
    case _: HttpRequest                                ⇒ HttpResponse(404, entity = "Unknown resource!")
  }
}

