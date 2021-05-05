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
    fun `0x6D adc 1 + $ABCD absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x6D))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x6D adc 1 + $ABCD + carry absolute`() {
        cpu.a.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x6D))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x7D adc 1 + $ABCD,X absolute x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x7D))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x7D adc 1 + $ABCD,X + carry absolute x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x7D))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x79 adc 1 + $ABCD,Y absolute y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x79))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x79 adc 1 + $ABCD,Y + carry absolute y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x79))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x61 adc 1 + ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x61))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x61 adc 1 + ($09, X) + carry indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x61))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x71 adc 1 + ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x71))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x71 adc 1 + ($0A),Y + carry indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x71))
        assertEquals(3, cpu.a.value)
    }

    @Test
    fun `0x29 and 1 with 1 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x29))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x29 and 1 with 0 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x29))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x25 and 1 with 1 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x25))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x25 and 1 with 0 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x25))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x35 and 1 with 1 zeropage, X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x35))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x35 and 1 with 0 zeropage, X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x35))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x2D and 1 + 1 $ABCD absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x2D))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x2D and 1 + 0 $ABCD absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x2D))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x3D and 1 + 1 $ABCD absolute x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x3D))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x3D and 1 + 0 $ABCD absolute x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x3D))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x39 and 1 + 1 $ABCD absolute y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x39))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x39 and 1 + 0 $ABCD absolute y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x39))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x21 and 1 with 1 ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x21))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x21 and 1 with 0 ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x21))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x31 and 1 with 1 ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x31))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x31 and 1 with 0 ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x31))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x0A asl 1 accumulator`() {
        cpu.a.value = 1
        cpu.execute(UnsignedByte(0x0A))
        assertEquals(2, cpu.a.value)
    }

    @Test
    fun `0x06 asl 1 zeropage`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x06))
        assertEquals(2, cpu.mmu.at(1).value)
    }

    @Test
    fun `0x16 asl 1 zeropage x`() {
        cpu.x.value = 1
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x16))
        assertEquals(2, cpu.mmu.at(1).value)
    }

    @Test
    fun `0x0E asl 1 absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x0E))
        assertEquals(2, cpu.mmu.at(0xABCD).value)
    }

    @Test
    fun `0x1E asl 1 absolute x`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x1E))
        assertEquals(2, cpu.mmu.at(0xABCE).value)
    }

    @Test
    fun `0x18 clear carry flag`() {
        cpu.carryFlag = true
        cpu.execute(UnsignedByte(0x18))
        assertFalse(cpu.carryFlag)
    }

    @Test
    fun `0x38 set carry flag`() {
        cpu.carryFlag = false
        cpu.execute(UnsignedByte(0x38))
        assertTrue(cpu.carryFlag)
    }

    @Test
    fun `0x58 clear interrupt disable flag`() {
        cpu.interruptDisableFlag = true
        cpu.execute(UnsignedByte(0x58))
        assertFalse(cpu.interruptDisableFlag)
    }

    @Test
    fun `0x78 set interrupt disable flag`() {
        cpu.interruptDisableFlag = false
        cpu.execute(UnsignedByte(0x78))
        assertTrue(cpu.interruptDisableFlag)
    }

    @Test
    fun `0xB8 clear overflow flag`() {
        cpu.overflowFlag = true
        cpu.execute(UnsignedByte(0xB8))
        assertFalse(cpu.overflowFlag)
    }

    @Test
    fun `0xD8 clear decimal flag`() {
        cpu.decimalFlag = true
        cpu.execute(UnsignedByte(0xD8))
        assertFalse(cpu.decimalFlag)
    }

    @Test
    fun `0xF8 set decimal flag`() {
        cpu.decimalFlag = false
        cpu.execute(UnsignedByte(0xF8))
        assertTrue(cpu.decimalFlag)
    }

    @Test
    fun `0x24 bit test 1 and 1 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x24))
        assertFalse(cpu.zeroFlag)
    }

    @Test
    fun `0x24 bit test 1 and 0 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x24))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0x2C bit test 1 and 1 absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x2C))
        assertFalse(cpu.zeroFlag)
    }

    @Test
    fun `0x2C bit test 1 and 0 absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(0))
        cpu.execute(UnsignedByte(0x2C))
        assertTrue(cpu.zeroFlag)
    }
}