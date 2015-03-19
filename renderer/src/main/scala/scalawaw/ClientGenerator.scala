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

class ClientGenerator(request: Request,response:Response) {

  private val sb : StringBuilder= new StringBuilder




   def render : Unit = {
     val src = Source.fromFile("./renderer/src/main/resources/Client.template")
     val out = new PrintWriter("./runner/src/main/scala/scalawaw/Client.scala", "UTF-8")
     val lines = src.getLines()

     val replaces = List(
       ("""URL""", request.urlPattern),
       ("""STATUS_CODE""", response.status.toString)
     )


     lines.map(line => replaces.foldLeft(line)((line,reps) => line.replaceAll(reps._1,reps._2))).foreach {out.println(_)}
     src.close()
     out.close()
  }
}


