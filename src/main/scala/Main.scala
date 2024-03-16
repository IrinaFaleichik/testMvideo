import scala.Option.when
import scala.collection.immutable.ListMap
import scala.io.Source
import scala.language.postfixOps
import scala.util.{Try, Using}

object Main {

  def main(args: Array[String]): Unit = {
    println(handleTheFlow(args))
  }

  def handleTheFlow(args: Array[String]): Either[Throwable, Map[String, Int]] = for {
    filename <- args.headOption toRight Errors.NoArguments
    _ <- when(filename.nonEmpty)(filename) toRight Errors.EmptyFilename
    readFile <- readFile(filename).toEither
    _ <- when(readFile.nonEmpty)(filename) toRight Errors.EmptyFile
    result = transformText(readFile)
    _ <- when(result.nonEmpty)(filename) toRight Errors.EmptyAfterFilter
  } yield result

  def readFile(filePath: String): Try[List[String]] = {
    Using(Source.fromFile(filePath)) { source =>
      source.getLines().toList
    }
  }

  def transformText(strs: List[String]): Map[String, Int] = {
    strs.flatMap(str => str.toLowerCase.split("[^а-яa-z\\d\\\\s]"))
      .filterNot(_.isEmpty)
      .foldLeft(ListMap[String, Int]()) { (acc, word) =>
        val counter = acc.getOrElse(word, 0)
        acc.updated(word, counter + 1)
      }
  }
}
