import scala.Option.when
import scala.collection.immutable.ListMap
import scala.language.postfixOps
import Scope._

object Main extends Scope.Default {

  def main(args: Array[String]): Unit = {
    handleTheFlow(args) match {
      case Left(ex) => Console.err.println(ex.getMessage)
      case Right(value) => Console.println(value)
    }
  }

  def handleTheFlow(args: Array[String]): Either[Throwable, Map[String, Int]] = for {
    filename <- args.headOption toRight Errors.NoArguments
    _ <- when(args.length == 1)() toRight Errors.TooManyArguments

    reader <- defaultReader(filename)
    textRead <- reader.read() toEither

    _ <- when(textRead.nonEmpty)(filename) toRight Errors.EmptyFile
    result = transformText(textRead)(formatter)
    _ <- when(result.nonEmpty)(filename) toRight Errors.EmptyAfterFilter
  } yield result

  def transformText(str: String)(implicit formatter: String): Map[String, Int] = {
    str
      .toLowerCase
      .split(formatter)
      .filterNot(_.isEmpty)
      .foldLeft(ListMap[String, Int]()) { (acc, word) =>
        val counter = acc.getOrElse(word, 0)
        acc.updated(word, counter + 1)
      }
  }
}
