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

    @Test
    fun `test carry flag on status method`() {
        cpu.carryFlag = true
        assertEquals(1, cpu.status().value)
    }

    @Test
    fun `test zero flag on status method`() {
        cpu.zeroFlag = true
        assertEquals(2, cpu.status().value)
    }

    @Test
    fun `test interrupt disable flag on status method`() {
        cpu.interruptDisableFlag = true
        assertEquals(4, cpu.status().value)
    }

    @Test
    fun `test decimal flag on status method`() {
        cpu.decimalFlag = true
        assertEquals(8, cpu.status().value)
    }

    @Test
    fun `test overflow flag on status method`() {
        cpu.overflowFlag = true
        assertEquals(64, cpu.status().value)
    }

    @Test
    fun `test negative flag on status method`() {
        cpu.negativeFlag = true
        assertEquals(128, cpu.status().value)
    }

    @Test
    fun `test all flags for status method`() {
        cpu.carryFlag = true
        cpu.zeroFlag = true
        cpu.interruptDisableFlag = true
        cpu.decimalFlag = true
        cpu.overflowFlag = true
        cpu.negativeFlag = true

        assertEquals(0b11001111, cpu.status().value)
        assertEquals(0b11001111, cpu.sr.value)
    }

    @Test
    fun `step pc forward one`() {
        cpu.step()
        assertEquals(1, cpu.pc.value)
    }

    @Test
    fun `add a byte to memory and step it`() {
        cpu.mmu.at(0)?.value = 1
        val current = cpu.step()
        assertEquals(1, current?.value)
        assertEquals(cpu.pc.value, 1)
    }

    @Test
    fun `add 2 bytes to memory and peek last`() {
        cpu.mmu.at(0)?.value = 1
        cpu.mmu.at(1)?.value = 2
        val next = cpu.peek()
        assertEquals(2, next?.value)
        assertEquals(0, cpu.pc.value)
    }
}