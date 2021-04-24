package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UnsignedByteTest {
    private var byte = UnsignedByte(0)

    @BeforeEach
    fun setUp() {
        byte = UnsignedByte(0)
    }

    @Test
    fun `add 1 to zero byte`() {
        byte += 1
        assertEquals(1, byte.value)
    }

    @Test
    fun `add 256 to zero byte`() {
        byte += 256
        // wrap-over occurs when adding is 0xFF + n
        assertEquals(0, byte.value)
    }

    @Test
    fun `add 257 to zero byte`() {
        byte += 257
        // anything past 256 should wrap and remain
        assertEquals(1, byte.value)
    }
}