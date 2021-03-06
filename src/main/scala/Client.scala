package bintry

import com.ning.http.client.{ AsyncHandler, Response }
import dispatch.{ OkFunctionHandler, Http, Req }
import scala.concurrent.{ ExecutionContext, Future }

object Client {
  type Handler[T] = AsyncHandler[T]
  abstract class Completion[T: Rep] {
    def apply(): Future[T] =
      apply(implicitly[Rep[T]].map)
    def apply[T](f: Response => T): Future[T] =
      apply(new OkFunctionHandler(f))
    def apply[T]
      (handler: Client.Handler[T]): Future[T]
  }
}

abstract class Requests(
  credentials: Credentials, http: Http)
 (implicit ec: ExecutionContext)
  extends DefaultHosts
  with Methods {

  def request[T]
    (req: Req)
    (handler: Client.Handler[T]): Future[T] =
     http(credentials.sign(req) > handler)

  def complete[A: Rep](req: Req): Client.Completion[A] =
    new Client.Completion[A] {
      override def apply[T]
        (handler: Client.Handler[T]) =
         request(req)(handler)
    }
}

case class Client(
  user: String, token: String,
  private val http: Http = new Http)
 (implicit ec: ExecutionContext)
  extends Requests(BasicAuth(user, token), http) {
  /** releases http resources. once closed, this client may no longer be used */
  def close(): Unit = http.shutdown()
}
