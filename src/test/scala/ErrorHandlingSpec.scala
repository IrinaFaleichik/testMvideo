import org.scalatest.matchers.should
import Main.handleTheFlow
import org.scalatest.wordspec.AnyWordSpec

// todo replace hardcoded filenames?

class ErrorHandlingSpec extends AnyWordSpec with should.Matchers {
  "Validation" when {
    "there is no arguments" in {
      handleTheFlow(Array()) shouldBe Left(Errors.NoArguments)
    }

    "filename is empty" in {
      handleTheFlow(Array("")) shouldBe Left(Errors.EmptyFilename)
    }

    "file is empty" in {
      handleTheFlow(Array("empty.txt")) shouldBe Left(Errors.EmptyFile)
    }

    "given filename is a directory name" in {
      val toException = handleTheFlow(Array(".")) match {
        case Right(_) => new Exception(s"fail test: directory should not be read")
        case Left(ex) => ex
      }
      toException.getMessage should include("Is a directory")
    }

    "file not found" in {
      val toException = handleTheFlow(Array("randomName")) match {
        case Right(_) => new Exception(s"fail test: directory should not be read")
        case Left(ex) => ex
      }
      toException.getMessage should include("No such file or directory")
    }

    "file after filtering" in {
      handleTheFlow(Array("NoAlphabeticSymbols.txt")) shouldBe Left(Errors.EmptyAfterFilter)
    }

    "successful result" in {
      handleTheFlow(Array("text.txt")).isRight shouldBe true
    }
  }
}
