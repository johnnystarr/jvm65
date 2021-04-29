package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class UnsignedRegisterTest {
    private lateinit var register: UnsignedRegister

    @BeforeEach
    fun setUp() {
        register = UnsignedRegister(0)
    }

    @Test
    fun `shift 1 left 1`() {
        register.value = 1
        register.shiftLeft()
        assertEquals(2, register.value)
    }

    @Test
    fun `shift 2 left 1`() {
        register.value = 2
        register.shiftLeft()
        assertEquals(4, register.value)
    }

    @Test
    fun `shift 1 right 1`() {
        register.value = 1
        register.shiftRight()
        assertEquals(0, register.value)
    }

    @Test
    fun `shift 2 right 1`() {
        register.value = 2
        register.shiftRight()
        assertEquals(1, register.value)
    }

    @Test
    fun `set register to zero`() {
        register.value = 1
        register.clear()
        assertEquals(0, register.value)
    }

    @Test
    fun `and operation 1 to 1`() {
        register.value = 1
        val result = register.and(UnsignedRegister(1))
        assertEquals(1, result.value)
    }

    @Test
    fun `and operation 0xFF to 0xFF all ones`() {
        register.value = 0xFF
        val result = register.and(UnsignedRegister(0xFF))
        assertEquals(0xFF, result.value)
    }

    @Test
    fun `or operation 1 to 0`() {
        assertEquals(1, register.or(UnsignedRegister(1)).value)
    }

    @Test
    fun `xor operation 1 to 1`() {
        register.value = 1
        assertEquals(0, register.xor(UnsignedRegister(1)).value)
    }
}