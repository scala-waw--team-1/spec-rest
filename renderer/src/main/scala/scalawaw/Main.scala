package scalawaw
/**
 * Created by bka on 12.02.15.
 */

import spray.json._
import DefaultJsonProtocol._

import scala.io.Source


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
    new ClientGenerator(spec.request,spec.response).render
  }

  // def runService = {
  //   Boot.run
  //   Client.run
  // }

  def main(args: Array[String]) : Unit = render
 

}

