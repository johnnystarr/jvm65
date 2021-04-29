package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class InstructionSetTest {
    private lateinit var cpu: P6502

    @BeforeEach
    fun setUp() {
        cpu = P6502()
    }

    @Test
    fun `0x69 adc 1 + 1 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x69))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x69 adc 1 + 1 + carry immediate`() {
        cpu.a.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x69))
        assertEquals(3, cpu.a.value)
    }
}