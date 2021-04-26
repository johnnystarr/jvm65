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
        assertEquals(RegisterState.NONE, byte.state)
    }

    @Test
    fun `add 256 to zero byte`() {
        byte += 256
        // wrap-over occurs when adding is 0xFF + n
        assertEquals(0, byte.value)
        assertEquals(RegisterState.ZEROED, byte.state)
    }

    @Test
    fun `add 257 to zero byte`() {
        byte += 257
        // anything past 256 should wrap and remain
        assertEquals(1, byte.value)
        assertEquals(RegisterState.POSITIVE_WRAPAROUND, byte.state)
    }

    @Test
    fun `add 1 to -1`() {
        byte.value = -1
        byte += 1
        assertEquals(0, byte.value)
        assertEquals(RegisterState.ZEROED, byte.state)
    }

    @Test
    fun `subtract 1 from zero byte`() {
        byte -= 1
        assertEquals(0xFF, byte.value)
        assertEquals(RegisterState.NEGATIVE_WRAPAROUND, byte.state)
    }

    @Test
    fun `subtract 2 from zero byte`() {
        byte -= 2
        assertEquals(0xFE, byte.value)
        assertEquals(RegisterState.NEGATIVE_WRAPAROUND, byte.state)
    }

    @Test
    fun `positive subtraction 5 from 10`() {
        byte.value = 10
        byte -= 5
        assertEquals(5, byte.value)
        assertEquals(RegisterState.NONE, byte.state)
    }

    @Test
    fun `subtract 1 from 1 to zero`() {
        byte.value = 1
        byte -= 1
        assertEquals(0, byte.value)
        assertEquals(RegisterState.ZEROED, byte.state)
    }

    @Test
    fun `shift 1 left 1`() {
        byte.value = 1
        byte.shiftLeft()
        assertEquals(2, byte.value)
        assertEquals(RegisterState.NONE, byte.state)
    }

    @Test
    fun `shift 2 left 1`() {
        byte.value = 2
        byte.shiftLeft()
        assertEquals(4, byte.value)
        assertEquals(RegisterState.NONE, byte.state)
    }

    @Test
    fun `shift 1 right 1`() {
        byte.value = 1
        byte.shiftRight()
        assertEquals(0, byte.value)
        assertEquals(RegisterState.ZEROED, byte.state)
    }

    @Test
    fun `shift 2 right 1`() {
        byte.value = 2
        byte.shiftRight()
        assertEquals(1, byte.value)
        assertEquals(RegisterState.NONE, byte.state)
    }

    @Test
    fun `set byte to zero`() {
        byte.value = 1
        byte.clear()
        assertEquals(0, byte.value)
        assertEquals(RegisterState.ZEROED, byte.state)
    }
}