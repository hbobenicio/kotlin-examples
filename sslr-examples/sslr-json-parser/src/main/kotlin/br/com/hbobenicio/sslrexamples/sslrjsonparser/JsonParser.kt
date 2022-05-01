package br.com.hbobenicio.sslrexamples.sslrjsonparser

import com.sonar.sslr.api.Grammar
import com.sonar.sslr.impl.Parser

fun createJSONParser(): Parser<Grammar> = Parser.builder(createJSONGrammar())
    .withLexer(createJSONLexer())
    .build()
