package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MemoryManagerTest {
    private lateinit var mmu: MemoryManager

    @BeforeEach
    fun setUp() {
        mmu = MemoryManager(0xFFFF, P6502())
    }

    @Test
    fun `validate mmu memory size`() {
        assertEquals(0x10000, mmu.memory.size)
    }

    @Test
    fun `set final memory address`() {
        mmu.put(0xFFFF, UnsignedByte(1))
        assertEquals(1, mmu.at(0xFFFF)?.value)
    }

    @Test
    fun `set memory address and cpu x to test atX`() {
        mmu.put(2, UnsignedByte(1))
        mmu.cpu.x.value = 1
        assertEquals(1, mmu.atX(1)?.value)
    }

    @Test
    fun `set memory address and cpu y to test atY`() {
        mmu.put(2, UnsignedByte(1))
        mmu.cpu.y.value = 1
        assertEquals(1, mmu.atY(1)?.value)
    }
}