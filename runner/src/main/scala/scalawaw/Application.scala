package scalawaw
object Application extends App {
	Boot.run
	Thread.sleep(1000)
	Client.run
}