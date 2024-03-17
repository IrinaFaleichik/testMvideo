object Scope {

  trait Default {
    implicit val formatter: String = "[^а-яa-z\\d\\\\s]"

    val defaultReader: String => Either[Throwable, DataReader] =
      filename => DataReaderFactory.createReader(ReaderType.Default, filename)
  }

  case object Errors {
    object NoArguments extends Exception("No arguments")

    object TooManyArguments extends Exception("Too many arguments. Expected 1")

    object EmptyFilename extends Exception("Invalid input: filename should not be empty")

    object EmptyFile extends Exception("Invalid file: text is empty")

    object EmptyAfterFilter extends Exception("Invalid file: text is empty after filtering")
  }

}

