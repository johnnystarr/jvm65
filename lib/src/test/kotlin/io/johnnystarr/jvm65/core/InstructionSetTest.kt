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

    @Test
    fun `0x65 adc 1 + $00 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x65))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x65 adc 1 + $00 + carry zeropage`() {
        cpu.a.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x65))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x75 adc 1 + $00,X zeropage`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0B, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x75))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x75 adc 1 + $00,X + carry zeropage`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0B, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x75))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x6D adc 1 + $ABCD + carry absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x6D))
        assertEquals(2, cpu.a.value)
    }
}