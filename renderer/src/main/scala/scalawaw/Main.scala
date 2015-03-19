package scalawaw
/**
 * Created by bka on 12.02.15.
 */

import spray.json._
import DefaultJsonProtocol._

import scala.io.Source

case class Request(method: String, urlPattern: String)
case class Response(status: Int, body: String)
case class Spec(request: Request, response: Response)

object SpecJsonProtocol extends DefaultJsonProtocol {
  implicit val requestFormat = jsonFormat2(Request.apply)
  implicit val responseFormat = jsonFormat2(Response.apply)
  implicit val specFormat = jsonFormat2(Spec.apply)
}

object MainApp {
  import SpecJsonProtocol._
  def getSpec = {
    val jsonStr = Source.fromFile("./renderer/src/main/resources/spec.json").mkString
    val json = jsonStr.parseJson
    json.convertTo[Spec]
  }

  def render = {
    val spec = getSpec
    ServiceRenderer.render(spec)
    new ClientGenerator(spec.request).render
  }

  // def runService = {
  //   Boot.run
  //   Client.run
  // }

  def main(args: Array[String]) : Unit = render
 

}

