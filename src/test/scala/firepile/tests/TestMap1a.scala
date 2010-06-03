package firepile.tests

import firepile._
import firepile.Device
import firepile.Spaces._
import firepile.util.BufferBackedArray._

object TestMap1a {
  val floatX2 = (a:Float) => a * 2.0f

  def main(args: Array[String]) = {
    implicit val gpu: Device = firepile.gpu

    val dataSize = if (args.length > 0) args(0).toInt else 1000

    val a = Array.tabulate(dataSize)(_.toFloat)

    println("cl array");
    {
      val c = time {
        val result = a.mapKernel(floatX2).start
        result.force
      }
      assert(a.length == c.length)
      for (i <- 0 until a.length) {
        println(a(i) + " " + c(i))
        assert((a(i)*2.f - c(i)).abs < 1e-6)
      }
    }
  }
}
