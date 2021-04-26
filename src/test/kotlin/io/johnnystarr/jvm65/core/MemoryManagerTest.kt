package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MemoryManagerTest {
    private lateinit var mmu: MMU

    @BeforeEach
    fun setUp() {
        mmu = MemoryManager(0xFFFF)
    }

    @Test
    fun `validate mmu memory size`() {
        assertEquals(0x10000, mmu.memory.size)
    }

    @Test
    fun `set final memory address`() {
        mmu.memory[0xFFFF]?.value = 1
        assertEquals(1, mmu.memory[0xFFFF]?.value)
    }
}