package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class P6502Test {
    private lateinit var cpu: P6502

    @BeforeEach
    fun setUp() {
        cpu = P6502()
    }

    @Test
    fun `P6502 default values`() {
        assertFalse(cpu.carryFlag)
        assertFalse(cpu.zeroFlag)
        assertFalse(cpu.decimalFlag)
        assertFalse(cpu.overflowFlag)
        assertFalse(cpu.negativeFlag)
        assertEquals(0, cpu.a.value)
        assertEquals(0, cpu.x.value)
        assertEquals(0, cpu.y.value)
        assertEquals(0, cpu.sr.value)
        assertEquals(0, cpu.pc.value)
    }
}