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
    fun `fetch byte and step cpu forward one`() {
        cpu.mmu.put(0, UnsignedByte(1))
        val byte = cpu.fetch()
        assertEquals(1, cpu.pc.value)
        assertEquals(1, byte.value)
        assertEquals(1, cpu.pc.value)
    }

    @Test
    fun `fetch word and step cpu forward two`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        val word = cpu.fetchWord()
        assertEquals(0xABCD, word.value)
        assertEquals(2, cpu.pc.value)
    }

    @Test
    fun `fetch word indirectly and step cpu forward one`() {
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        val word = cpu.fetchWordIndirect(useX = false)
        assertEquals(0xABCD, word.value)
        assertEquals(1, cpu.pc.value)
    }

    @Test
    fun `fetch word indirectly indexed by x and step cpu forward one`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        val word = cpu.fetchWordIndirect(useX = true)
        assertEquals(0xABCD, word.value)
        assertEquals(1, cpu.pc.value)
    }

    @Test
    fun `add a byte to memory and fetch it`() {
        cpu.mmu.put(0, UnsignedByte(1))
        val current = cpu.fetch()
        assertEquals(1, current.value)
        assertEquals(cpu.pc.value, 1)
    }

    @Test
    fun `add 2 bytes to memory and peek last`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(2))
        val next = cpu.peek()
        assertEquals(2, next.value)
        assertEquals(0, cpu.pc.value)
    }

    @Test
    fun `little endian evaluation`() {
        val msb = UnsignedByte(0xCD)
        val lsb = UnsignedByte(0xAB)
        val littleEndian = cpu.littleEndian(msb, lsb)
        assertEquals(0xABCD, littleEndian.value)
    }

    @Test
    fun `split word into bytes`() {
        val word = UnsignedWord(0xABCD)
        val bytes = cpu.splitWord(word)
        assertEquals(0xAB, bytes[0].value)
        assertEquals(0xCD, bytes[1].value)
    }
}