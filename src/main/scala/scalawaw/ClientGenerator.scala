package scalawaw

import java.io.PrintWriter

import spray.http._
import spray.json.DefaultJsonProtocol
import spray.httpx.encoding.{Gzip, Deflate}
import spray.httpx.SprayJsonSupport._
import spray.client.pipelining._
import spray.json._
import DefaultJsonProtocol._
import java.io.File

import scala.io.Source

/**
 * Created by tomaszk on 2/12/15.
 */

class ClientGenerator(request: Request) {

  private val sb : StringBuilder= new StringBuilder


  private def insertImports(): Unit = {
    sb.append("""package scalawaw
                |import akka.actor.ActorSystem
                |import spray.http._
                |import spray.json.DefaultJsonProtocol
                |import spray.httpx.encoding.{Gzip, Deflate}
                |import spray.httpx.SprayJsonSupport._
                |import spray.client.pipelining._
                |import scala.concurrent.Future
                |import SpecJsonProtocol._
                |""")
  }

  private def startMain() : Unit = {
    sb.append(
      """object Client extends App {
      """.stripMargin)
  }


  private def insertActorSystem(): Unit = {
    sb.append("""|implicit val system = ActorSystem()
                |import system.dispatcher
                |""")
  }


  private def buildPipeline() : Unit = {
    sb.append("""val pipeline: HttpRequest => Future[Response] = (
                | sendReceive
                |  ~> unmarshal[Response]
                |)
                |""")
  }

  private def addResponseCode() : Unit = {

    sb.append(s"""val response: Future[Response] = pipeline(${request.method}("http://localhost:8080${request.urlPattern}"))
                |""")
  }

  private def close() : Unit = {
    sb.append(
      """
        |}
      """.stripMargin)
  }

  private def render : String = {
    insertImports();
    startMain()
    insertActorSystem();
    buildPipeline();
    addResponseCode()
    close()
    return sb.stripMargin
  }


}



object ClientGenerator extends App {
  println("Generating client source:")
  import SpecJsonProtocol._
  val jsonStr = Source.fromFile("./src/main/resources/spec.json").mkString
  val json = jsonStr.parseJson
  val spec = json.convertTo[Spec]
  println(spec.response.status)
  val cg = new ClientGenerator(spec.request)
  val outFile: File = new File("./src/main/scala/scalawaw/Client.scala")
  if(outFile.exists())
    outFile.delete()
  outFile.getParentFile.mkdir()
  val pw : PrintWriter = new PrintWriter(outFile)
  pw.write(cg.render)
  pw.close()

}
