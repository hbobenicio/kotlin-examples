package br.com.hbobenicio.sslrexamples.sslrjsonparser.unit

import br.com.hbobenicio.sslrexamples.sslrjsonparser.Literals
import br.com.hbobenicio.sslrexamples.sslrjsonparser.createJSONLexer
import com.sonar.sslr.api.Token
import com.sonar.sslr.impl.Lexer
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonLexerTest {

    private var lexer: Lexer? = null

    @BeforeTest
    fun setup() {
        lexer = createJSONLexer()
    }

    @Test
    fun testJustOneToken() {
        val testCases = listOf(
            Pair("null", Literals.NULL),
            Pair("true", Literals.TRUE),
            Pair("false", Literals.FALSE),
            Pair("42", Literals.INTEGER_LITERAL),
            Pair("-42", Literals.INTEGER_LITERAL),
            Pair("3.1415", Literals.FLOAT_LITERAL),
            Pair("42.00", Literals.FLOAT_LITERAL),
            Pair("\"Hello, World!\"", Literals.DOUBLE_QUOTED_STRING_LITERAL),
            Pair("'Hello, World!'", Literals.SINGLE_QUOTED_STRING_LITERAL),
        )
        testCases.forEach { (input, expectedTokenType) ->
            lexer = createJSONLexer()

            val tokens = lexer!!.lex(input)
            assertEquals(2, tokens.size)

            val actualToken = tokens[0]
            assertEquals(expectedTokenType, actualToken.type)
            assertEquals(input, actualToken.value)
        }
    }

    @Test
    fun testOtherCases() {
        val input = "{\"hello\": null}"
        val tokens: List<Token> = lexer!!.lex(input)
        println(tokens)
    }
}