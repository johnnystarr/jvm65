package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FlagsCheckTest {
    private lateinit var cpu: P6502

    @BeforeEach
    fun setUp() {
        cpu = P6502()
    }

    @Test
    fun `0x69 add 1 + 0xFF zero flag test`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xFF))
        cpu.execute(UnsignedByte(0x69))
        assertTrue(cpu.zeroFlag)
    }
}