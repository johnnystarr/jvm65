package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UnsignedWordTest {
    private var word = UnsignedWord(0)

    @BeforeEach
    fun setUp() {
        word = UnsignedWord(0)
    }

    @Test
    fun `add 1 to zero word`() {
        word += 1
        assertEquals(1, word.value)
    }

    @Test
    fun `add 65536 to zero word`() {
        word += 65536
        // wrap-over occurs when adding is 0xFFFF + n
        assertEquals(0, word.value)
    }

    @Test
    fun `add 65537 to zero word`() {
        word += 65537
        // anything past 256 should wrap and remain
        assertEquals(1, word.value)
    }

    @Test
    fun `subtract 1 from zero word`() {
        word -= 1
        assertEquals(0xFFFF, word.value)
    }

    @Test
    fun `subtract 2 from zero word`() {
        word -= 2
        assertEquals(0xFFFE, word.value)
    }

    @Test
    fun `positive subtraction 5 from 10`() {
        word.value = 10
        word -= 5
        assertEquals(5, word.value)
    }
}