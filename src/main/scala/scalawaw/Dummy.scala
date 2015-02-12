package scalawaw

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

/**
 * Created by tomaszk on 2/12/15.
 */
object Dummy extends App with SimpleRoutingApp {
implicit val system = ActorSystem("my-system")

startServer(interface = "localhost", port = 8080) {
path("hello") {
get {
complete {
"Say hello to spray"
}
}
}
}
}
