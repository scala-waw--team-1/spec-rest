package scalawaw
import akka.actor.ActorSystem
import spray.http._
import spray.json.DefaultJsonProtocol
import spray.httpx.encoding.{Gzip, Deflate}
import spray.httpx.SprayJsonSupport._
import spray.client.pipelining._
import scala.concurrent.Future
import SpecJsonProtocol._
object Client {
def run {
implicit val system = ActorSystem()
import system.dispatcher
val pipeline: HttpRequest => Future[Response] = (
 sendReceive
  ~> unmarshal[Response]
)
val response: Future[Response] = pipeline(Get("http://localhost:8080/hello"))
 println("Got response")
              
}
}
      