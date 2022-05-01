package br.com.hbobenicio.sslrexamples.sslrjsonparser

import com.sonar.sslr.api.Grammar
import org.sonar.sslr.grammar.GrammarRuleKey
import org.sonar.sslr.grammar.LexerfulGrammarBuilder

import br.com.hbobenicio.sslrexamples.sslrjsonparser.JsonGrammarRules.*
import br.com.hbobenicio.sslrexamples.sslrjsonparser.Punctuations.*
import br.com.hbobenicio.sslrexamples.sslrjsonparser.Literals.*

enum class JsonGrammarRules: GrammarRuleKey {
    Value,
    Object,
    Array,
    Literal,
    BooleanLiteral,
    StringLiteral,
    NumberLiteral;
}

fun createJSONGrammar(): Grammar {
    val b = LexerfulGrammarBuilder.create()

    b.rule(BooleanLiteral).`is`(
        b.firstOf(TRUE, FALSE)
    )

    b.rule(NumberLiteral).`is`(
        b.firstOf(
            FLOAT_LITERAL,
            INTEGER_LITERAL
        )
    )

    b.rule(StringLiteral).`is`(
        b.firstOf(
            SINGLE_QUOTED_STRING_LITERAL,
            DOUBLE_QUOTED_STRING_LITERAL,
        )
    )

    b.rule(Literal).`is`(
        b.firstOf(
            NULL,
            BooleanLiteral,
            StringLiteral,
            NumberLiteral,
        )
    )

    b.rule(Array).`is`(
        b.firstOf(
            b.sequence(OPEN_BRACKET, CLOSE_BRACKET),
            b.sequence(
                OPEN_BRACKET,
                Value,
                b.zeroOrMore(
                    b.optional(COMMA), Value
                ),
                CLOSE_BRACKET,
            )
        )
    )

    b.rule(Object).`is`(
        b.firstOf(
            b.sequence(OPEN_BRACE, CLOSE_BRACE),
            b.sequence(
                OPEN_BRACE,
                StringLiteral, COLON, Value,
                b.zeroOrMore(
                    b.optional(COMMA), StringLiteral, COLON, Value
                ),
                CLOSE_BRACE,
            )
        )
    )

    b.rule(Value).`is`(
        b.firstOf(
            Literal,
            Object,
            Array,
        )
    )

    b.setRootRule(Value)

    return b.build()
}
