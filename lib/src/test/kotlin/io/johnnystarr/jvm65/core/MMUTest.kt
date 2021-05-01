package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MMUTest {
    private lateinit var mmu: MMU

    @BeforeEach
    fun setUp() {
        mmu = P6502().mmu
    }

    @Test
    fun `validate mmu memory size`() {
        assertEquals(0x10000, mmu.memory.size)
    }

    @Test
    fun `set final memory address`() {
        mmu.put(0xFFFF, UnsignedByte(1))
        assertEquals(1, mmu.at(0xFFFF).value)
    }

    @Test
    fun `set memory address and cpu x to test atX`() {
        mmu.put(2, UnsignedByte(1))
        mmu.cpu.x.value = 1
        assertEquals(1, mmu.atX(1).value)
    }

    @Test
    fun `set memory address and cpu y to test atY`() {
        mmu.put(2, UnsignedByte(1))
        mmu.cpu.y.value = 1
        assertEquals(1, mmu.atY(1).value)
    }

    @Test
    fun `immediate address mode`() {
        mmu.put(0, UnsignedByte(1))
        val byte = mmu.immediate()
        assertEquals(1, byte.value)
    }
}