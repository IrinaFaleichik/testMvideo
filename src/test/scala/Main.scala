
import zio.{Task, URIO, ZIO}

import scala.collection.immutable.ListMap
import scala.io.Source
import zio._

/*
*
* добавить scalafmt
* добавить тесты на валидацию
*
* */

object Main extends App {

  def run(args: List[String]): URIO[zio.ZEnv, zio.ExitCode] =
    for {
      exitCode <- ZIO
        .effectTotal(if (args.isEmpty) throw new IllegalArgumentException("No file path provided"))
        .andThen(readFile(args.head))
        .map(Main.transformText)
        .map(println)
        .exitCode
    } yield exitCode

  def transformText(strs: List[String]): Map[String, Int] = {
    strs
      .flatMap(str => str.toLowerCase.split("[^а-яa-z\\d\\\\s]"))
      .filterNot(_.isEmpty)
      .foldLeft(ListMap[String, Int]()) { (acc, word) =>
        val counter = acc.getOrElse(word, 0)
        acc.updated(word, counter + 1)
      }
  }

  def readFile(filePath: String): Task[List[String]] = Task {
    val source = Source.fromFile(filePath)
    try source.getLines().toList finally source.close()
  }

}
