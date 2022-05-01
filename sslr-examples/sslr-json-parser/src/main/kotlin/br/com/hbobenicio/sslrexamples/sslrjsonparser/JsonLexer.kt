package br.com.hbobenicio.sslrexamples.sslrjsonparser

import com.sonar.sslr.api.AstNode
import com.sonar.sslr.api.TokenType
import com.sonar.sslr.impl.Lexer
import com.sonar.sslr.impl.channel.BlackHoleChannel
import com.sonar.sslr.impl.channel.PunctuatorChannel
import com.sonar.sslr.impl.channel.RegexpChannelBuilder.*

enum class Literals: TokenType {
    SINGLE_QUOTED_STRING_LITERAL,
    DOUBLE_QUOTED_STRING_LITERAL,
    FLOAT_LITERAL,
    INTEGER_LITERAL,
    TRUE,
    FALSE,
    NULL;

    override fun getName(): String = this.name
    override fun getValue(): String = this.name
    override fun hasToBeSkippedFromAst(node: AstNode?): Boolean = false
}

enum class Punctuations(private val value: String): TokenType {
    OPEN_BRACE("{"),
    CLOSE_BRACE("}"),
    OPEN_BRACKET("["),
    CLOSE_BRACKET("]"),
    COMMA(","),
    COLON(":"),;

    override fun getName(): String = this.name
    override fun getValue(): String = this.value
    override fun hasToBeSkippedFromAst(node: AstNode?): Boolean = false
}

fun createJSONLexer(): Lexer = Lexer.builder()
    .withChannel(regexp(Literals.TRUE, "true"))
    .withChannel(regexp(Literals.FALSE, "false"))
    .withChannel(regexp(Literals.NULL, "null"))
    .withChannel(regexp(Literals.SINGLE_QUOTED_STRING_LITERAL, "'[^']*'"))
    .withChannel(regexp(Literals.DOUBLE_QUOTED_STRING_LITERAL, "\"[^\"]*\""))
    .withChannel(regexp(Literals.FLOAT_LITERAL, "[-]?\\d+\\.\\d+"))
    .withChannel(regexp(Literals.INTEGER_LITERAL, "[-]?\\d+"))
    .withChannel(PunctuatorChannel(*Punctuations.values()))
    .withChannel(BlackHoleChannel("\\s+"))
    .withFailIfNoChannelToConsumeOneCharacter(true)
    .build()
