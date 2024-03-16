import scala.collection.immutable.{HashMap, ListMap}
import scala.io.Source
import scala.util.{Failure, Success, Using}

object Main extends App {

  def reading(filePath: String = ".") = {
    Using(Source.fromFile(filePath)) { source =>
      val s = source.getLines()
        .flatMap(str => str.toLowerCase.split("[^а-яa-z\\d\\\\s]"))
        .filterNot(_.isEmpty)
        .toList

      val r = s
        .foldLeft(ListMap[String, Int]()) { (acc, word) =>
          val counter = acc.getOrElse(word, 0)
          acc.updated(word, counter + 1)
        }
      s.foreach(println)
      r.foreach(println)
    }
  }

  reading("./text.txt") match {
    case Success(v) => println(v)
    case Failure(ex) => println(ex)
  }
}
