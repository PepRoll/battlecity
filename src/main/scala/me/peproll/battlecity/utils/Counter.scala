package me.peproll.battlecity.utils

import scala.async.Async.{async, await}
import scala.concurrent.{Future, Promise}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

trait Counter[T] {
  def run(next: Int => Future[Unit]): Future[T]
}

object Counter {

  def apply[T](runCounter: CounterPromise[T] => Unit): Counter[T] = (next: (Int) => Future[Unit]) => {
    try {
      val promise = Promise[T]()

      runCounter(new CounterPromise[T] {

        var counter: Int = 0

        override def increment(): Future[Unit] = async {
          counter += 1
          await(next(counter))
        }

        override def complete(result: Try[T]): Unit = {
          promise.complete(result)
          ()
        }

        override def isCompleted: Boolean = promise.isCompleted
      })

      promise.future
    } catch {
      case NonFatal(ex) => Future.failed(ex)
    }
  }

  trait CounterPromise[T] {
    def increment(): Future[Unit]
    def complete(result: Try[T]): Unit
    def isCompleted: Boolean
    def success(r: T): Unit = complete(Success(r))
    def failure(ex: Throwable): Unit = complete(Failure(ex))
  }

}
