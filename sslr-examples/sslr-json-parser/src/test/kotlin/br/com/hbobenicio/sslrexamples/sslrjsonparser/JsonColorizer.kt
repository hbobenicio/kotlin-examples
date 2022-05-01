package br.com.hbobenicio.sslrexamples.sslrjsonparser

import org.sonar.colorizer.KeywordsTokenizer
import org.sonar.colorizer.LiteralTokenizer
import org.sonar.colorizer.Tokenizer

object JsonColorizer {
    val tokenizers: List<Tokenizer>
        get() = listOf(
//            CDocTokenizer("<span class=\"cd\">", "</span>"),
//            CppDocTokenizer("<span class=\"cppd\">", "</span>"),
            LiteralTokenizer("<span class=\"l\">", "</span>"),
            KeywordsTokenizer(
                "<span class=\"k\">", "</span>",
                *Literals.values().map(Literals::getName).toTypedArray()
            )
        )
}
