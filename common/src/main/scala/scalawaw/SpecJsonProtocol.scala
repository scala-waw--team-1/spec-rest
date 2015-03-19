package scalawaw

import spray.json._
import DefaultJsonProtocol._

case class Request(method: String, urlPattern: String)
case class Response(status: Int, body: String)
case class Spec(request: Request, response: Response)

object SpecJsonProtocol extends DefaultJsonProtocol {
  implicit val requestFormat = jsonFormat2(Request.apply)
  implicit val responseFormat = jsonFormat2(Response.apply)
  implicit val specFormat = jsonFormat2(Spec.apply)
}