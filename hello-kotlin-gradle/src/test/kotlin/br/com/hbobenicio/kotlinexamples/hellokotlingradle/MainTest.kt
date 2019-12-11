package br.com.hbobenicio.kotlinexamples.hellokotlingradle

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

@Tag("main")
class MainTest {

    @Test
    @DisplayName("foo must be foo")
    fun greetingMustBeDefined() {
        assertEquals("Hello, Kotlin!", App.greeting())
    }
}
