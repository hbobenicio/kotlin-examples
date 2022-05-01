package br.com.hbobenicio.sslrexamples.sslrjsonparser.unit

import br.com.hbobenicio.sslrexamples.sslrjsonparser.createJSONParser
import com.sonar.sslr.api.Grammar
import com.sonar.sslr.impl.Parser
import kotlin.test.BeforeTest
import kotlin.test.Test

import org.sonar.sslr.tests.Assertions.assertThat

class JsonGrammarTest {

    private var parser: Parser<Grammar>? = null
    private var grammar: Grammar? = null

    @BeforeTest
    fun setup() {
        parser = createJSONParser()
        grammar = parser!!.grammar
    }

    @Test
    fun testBasicCases() {
        assertThat(parser!!)
            .matches("null")
            .matches("true")
            .matches("false")
            .matches("123")
            .matches("123.456")
            .matches("'hello, world!'")
            .matches("\"hello, world!\"")
            .matches("[]")
            .matches("[1]")
            .matches("[1, 2, 3]")
            .matches(
                """
                    [
                        { "it": "works" },
                        { "yes": "it does" }
                    ]
                """.trimIndent()
            )
            .matches("{}")
            .matches("{\"hello\": null}")
            .matches("{\"hello\": true}")
            .matches("{\"hello\": false}")
            .matches("{\"hello\": 123}")
            .matches("{\"hello\": \"world\"}")
            .matches(
                """
                {
                    "string": "world",
                    "integer": 123,
                    "float": 123.456,
                    "null": null,
                    "true": true,
                    "false": false
                }
                """.trimIndent()
            )
    }
}
