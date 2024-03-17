import org.scalatest.matchers.should
import Main.handleTheFlow
import org.scalatest.wordspec.AnyWordSpec

class ErrorHandlingSpec extends AnyWordSpec with should.Matchers {

  import ErrorHandlingSpec._

  "Validation" when {
    "there is no arguments" in {
      handleTheFlow(Array()) shouldBe Left(Errors.NoArguments)
    }

    "filename is empty" in {
      handleTheFlow(Array("")) shouldBe Left(Errors.EmptyFilename)
    }

    "file is empty" in {
      handleTheFlow(Array(emptyFile)) shouldBe Left(Errors.EmptyFile)
    }

    "given filename is a directory name" in {
      val toException = handleTheFlow(Array(directoryName)) match {
        case Right(_) => new Exception(s"fail test: directory should not be read")
        case Left(ex) => ex
      }
      toException.getMessage should include("Is a directory")
    }

    "file not found" in {
      val toException = handleTheFlow(Array(madeupFilename)) match {
        case Right(_) => new Exception(s"fail test: directory should not be read")
        case Left(ex) => ex
      }
      toException.getMessage should include("No such file or directory")
    }

    "file is empty after filtering" in {
      handleTheFlow(Array(fileWithoutWords)) shouldBe Left(Errors.EmptyAfterFilter)
    }

    "successful result" in {
      handleTheFlow(Array(validFile)).isRight shouldBe true
    }
  }
}

object ErrorHandlingSpec {
  val resourcePath = "src/test/resources/"

  val emptyFile: String = resourcePath + "empty.txt"
  val directoryName: String = resourcePath + "."
  val madeupFilename: String = resourcePath + "randomName"
  val fileWithoutWords: String = resourcePath + "NoAlphabeticSymbols.txt"
  val validFile: String = resourcePath + "text.txt"
}
