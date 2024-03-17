import scala.Option.when
import scala.io.{Codec, Source}
import scala.util.{Try, Using}
import Scope.Errors

trait DataReader {
  def read(): Try[String]
}

private class DefaultReader(filePath: String)(implicit val codec: Codec) extends DataReader {
  def read(): Try[String] = {
    Using(Source.fromFile(filePath)) { source =>
      source.getLines().mkString
    }
  }
}

object DataReaderFactory {
  def createReader(readerType: ReaderType, filename: String): Either[Throwable, DataReader] = for {
    _ <- when(filename.nonEmpty)(filename) toRight Errors.EmptyFilename
  } yield readerType match {
    case ReaderType.Default => new DefaultReader(filename)
  }
}
