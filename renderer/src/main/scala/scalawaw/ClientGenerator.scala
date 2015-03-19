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
                |import scala.util.{Success,Failure}
                |""")
  }

  private def startMain() : Unit = {
    sb.append(
      """object Client {
      """.stripMargin)
  }

  private def insertActorSystem(): Unit = {
    sb.append("""|def run {
                |implicit val system = ActorSystem()
                |import system.dispatcher
                |""")
  }


  private def buildPipeline() : Unit = {
    sb.append(
      """val pipeline: HttpRequest => Future[HttpResponse] = sendReceive
        |
      """.stripMargin)
  }

  private def addResponseCode() : Unit = {

    sb.append(s"""val response: Future[HttpResponse] = pipeline(${request.method}("http://localhost:8080/${request.urlPattern}"))
                | response.onComplete { 
                |   case Success(x) =>
                |     println(x)
                |   case Failure(y) => 
                |     println(y)
                |}
              """)
  }

  private def close() : Unit = {
    sb.append(
      """
        |}
        |}
      """.stripMargin)
  }

   def render : Unit = {
    insertImports();
    startMain()
    insertActorSystem();
    buildPipeline();
    addResponseCode()
    close()
    val content : String =  sb.stripMargin
    val outFile: File = new File("./runner/src/main/scala/scalawaw/Client.scala")
      outFile.delete()
    val pw : PrintWriter = new PrintWriter(outFile)
    pw.write(content)
    pw.close()
  }
}


