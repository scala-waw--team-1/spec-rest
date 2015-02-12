/**
 * Created by bka on 12.02.15.
 */
import spray.json._
import DefaultJsonProtocol._

import scala.io.Source

case class Request(method: String, urlPattern: String)
case class Response(status: Int)
case class Spec(request: Request, response: Response)

object SpecJsonProtocol extends DefaultJsonProtocol {

  implicit val requestFormat = jsonFormat2(Request.apply)
  implicit val responseFormat = jsonFormat1(Response.apply)
  implicit val specFormat = jsonFormat2(Spec.apply)
}

object MainApp extends App {
  import SpecJsonProtocol._
  val jsonStr = Source.fromFile("./src/main/resources/spec.json").mkString
  val json = jsonStr.parseJson
  val spec = json.convertTo[Spec]
  println(spec.response.status)
}