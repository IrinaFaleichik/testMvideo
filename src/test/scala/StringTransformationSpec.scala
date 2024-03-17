import org.scalatest.matchers.should
import Main.transformText
import org.scalatest.wordspec.AnyWordSpec

class StringTransformationSpec extends AnyWordSpec with should.Matchers with Scope.Default {

  "StringTransformation" when {

    "basic test" in {
      transformText(
        "Это текстовый файл. Это второе предложение."
      ) shouldBe Map("это" -> 2,
        "текстовый" -> 1,
        "файл" -> 1,
        "второе" -> 1,
        "предложение" -> 1)
    }

    "empty text" in {
      transformText(
        " \r \t             \n\n\n\n"
      ) shouldBe Map()
    }
    "text consists of whitespaces" in {
      transformText("") shouldBe Map()
    }

    "text consists of special characters" in {
      transformText(
        "$$$$$$$$$$%%$%$%#@@......??"
      ) shouldBe Map()
    }

    "text is separated by special characters as well" in {
      transformText(
        "hello&&world...goodbye,,(the)()(((void"
      ) shouldBe Map("hello" -> 1, "world" -> 1, "goodbye" -> 1, "the" -> 1, "void" -> 1)
    }

    "text is transformed to lowercase" in {
      transformText(
        "HELLO WoRlD gOODbye         the vOID"
      ) shouldBe Map("hello" -> 1, "world" -> 1, "goodbye" -> 1, "the" -> 1, "void" -> 1)
    }

    "similar words are counted" in {
      transformText(
        s"${"hello " * 100} ${"hello " * 30} ${"hello " * 5}"
      ) shouldBe Map("hello" -> 135)
    }

    "similar words wrote in a different register are counted as one" in {
      transformText(
        s"${"HELLO hello " * 100} ${"HELLo hellO " * 30}  ${"HeLlo hElLo " * 5}"
      ) shouldBe Map("hello" -> 270)
    }
  }
  "words are added in the first-entry order" in {
    val transformedText = transformText("a c b").toList
    transformedText should not be List("c" -> 1, "b" -> 1, "a" -> 1)
    transformedText shouldBe List("a" -> 1, "c" -> 1, "b" -> 1)
  }

  "multi-entering of words doesn't change the order" in {
    val transformedTextA = transformText("a c b a a a a a a").toList
    val transformedTextB = transformText("a c b b b b b b b").toList
    val transformedTextC = transformText("a c b c c c c c c").toList

    transformedTextA should not be List("c" -> 1, "b" -> 1, "a" -> 7)
    transformedTextA shouldBe List("a" -> 7, "c" -> 1, "b" -> 1)

    transformedTextB should not be List("c" -> 1, "b" -> 7, "a" -> 1)
    transformedTextB shouldBe List("a" -> 1, "c" -> 1, "b" -> 7)

    transformedTextC should not be List("c" -> 7, "b" -> 1, "a" -> 1)
    transformedTextC shouldBe List("a" -> 1, "c" -> 7, "b" -> 1)
  }
}
