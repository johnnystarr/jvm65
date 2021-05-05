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
    fun `0x69 add 1 + 0xFF zero flag test (true)`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xFF))
        cpu.execute(UnsignedByte(0x69))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0x69 add 1 + 1 zero flag test (false)`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x69))
        assertFalse(cpu.zeroFlag)
    }

    @Test
    fun `0x69 add 1 + 0x7F negative flag test (true)`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0x7F))
        cpu.execute(UnsignedByte(0x69))
        assertTrue(cpu.negativeFlag)
    }

    @Test
    fun `0x69 add 1 + 1 negative flag test (false)`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x69))
        assertFalse(cpu.negativeFlag)
    }

    @Test
    fun `0x69 add 1 + 0x7F overflow flag test`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0x7F))
        cpu.execute(UnsignedByte(0x69))
        assertTrue(cpu.overflowFlag)
    }

    @Test
    fun `0x69 add 1 + 1 overflow flag test (false)`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x69))
        assertFalse(cpu.overflowFlag)
    }
}