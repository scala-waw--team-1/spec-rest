/**
 * Created by bka on 12.02.15.
 */
package scalawaw

import java.io.PrintWriter

import scala.io.Source

object ServiceRenderer {
  case class Path(url: String)
  sealed trait HttpMethod
  case object Get extends HttpMethod
  case class Body(text: String)
  case class Route(path: Path, method: HttpMethod, body: Body)

  def braces(str: String) = " { " + str + " } "
  def abbrev(str: String) = "\"" + str + "\""
  def paren(str: String) = "(" + str + ")"

  def getRoute(spec: Spec): Route = {
    val Spec(request, response, variables) = spec
    val path = Path(request.urlPattern)
    val method = Get
    val body = Body(response.body)
    Route(path, method, body)
  }

  def renderRoute(route: Route): String = {
    "path" + paren(abbrev(route.path.url)) + " " + braces(
      route.method.toString.toLowerCase() + braces(
      "respondWithMediaType(`text/html`)" + braces(
      "complete" + braces(abbrev(route.body.text))
        )
      )
    )
  }

  def render(spec: Spec) = {
    val src = Source.fromFile("./renderer/src/main/resources/Service.template")
    val out = new PrintWriter("./runner/src/main/scala/scalawaw/Service.scala", "UTF-8")
    val lines = src.getLines()
    val route = getRoute(spec)
    lines.map(_.replaceAll("ROUTE", renderRoute(route))).foreach {out.println(_)}
    src.close()
    out.close()
  }
}