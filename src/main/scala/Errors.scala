case object Errors {
  object NoArguments extends Exception("No arguments")

  object EmptyFilename extends Exception("Invalid input: filename should not be empty")

  object EmptyFile extends Exception("Invalid file: text is empty")

  object EmptyAfterFilter extends Exception("Invalid file: text is empty after filtering")
}
