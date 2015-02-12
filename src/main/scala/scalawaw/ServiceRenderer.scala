/**
 * Created by bka on 12.02.15.
 */
package scalawaw

import java.io.PrintWriter

import scala.io.Source

class ServiceRenderer(spec: Spec) {
  def braces(str: String) = " { " + str + " } "

  def getRoute = {
    val Spec(request, response) = spec
    "path(\"\") " + braces(
      request.method.toLowerCase() + braces(
      "respondWithMediaType(`text/html`)" + braces(
      "complete" + braces("\"a\"")
        )
      )
    )
  }

  def render = {
    val src = Source.fromFile("./src/main/resources/Service.template")
    val out = new PrintWriter("./src/main/scala/scalawaw/Service.scala", "UTF-8")
    val lines = src.getLines()
    val route = getRoute
    lines.map(_.replaceAll("ROUTE", route)).foreach {out.println(_)}
    src.close()
    out.close()
  }
}