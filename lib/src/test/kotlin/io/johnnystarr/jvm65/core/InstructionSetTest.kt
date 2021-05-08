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

    /**
     * ADC - Add with Carry
     */

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

    /**
     * AND - Logical AND
     */

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

    /**
     * ASL - Arithmetic Shift Left
     */

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

    /**
     * BIT Testing
     */

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

    /**
     * Bxx - Branch Operations
     */

    @Test
    fun `0x10 branch on plus (forward 4 bytes)`() {
        cpu.negativeFlag = false
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0x10))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0x10 branch on plus (backwards 4 bytes)`() {
        cpu.negativeFlag = false
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0x10))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0x30 branch on minus (forward 4 bytes)`() {
        cpu.negativeFlag = true
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0x30))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0x30 branch on minus (backwards 4 bytes)`() {
        cpu.negativeFlag = true
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0x30))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0x50 branch on overflow clear (forward 4 bytes)`() {
        cpu.overflowFlag = false
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0x50))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0x50 branch on overflow clear (backwards 4 bytes)`() {
        cpu.overflowFlag = false
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0x50))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0x70 branch on overflow set (forward 4 bytes)`() {
        cpu.overflowFlag = true
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0x70))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0x70 branch on overflow set (backwards 4 bytes)`() {
        cpu.overflowFlag = true
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0x70))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0x90 branch on carry clear (forward 4 bytes)`() {
        cpu.carryFlag = false
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0x90))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0x90 branch on carry clear (backwards 4 bytes)`() {
        cpu.carryFlag = false
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0x90))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0xB0 branch on carry set (forward 4 bytes)`() {
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0xB0))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0xB0 branch on carry set (backwards 4 bytes)`() {
        cpu.carryFlag = true
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0xB0))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0xD0 branch on not equal (forward 4 bytes)`() {
        cpu.zeroFlag = false
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0xD0))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0xD0 branch on not equal (backwards 4 bytes)`() {
        cpu.zeroFlag = false
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0xD0))
        assertEquals(6, cpu.pc.value)
    }

    @Test
    fun `0xF0 branch on equal (forward 4 bytes)`() {
        cpu.zeroFlag = true
        cpu.mmu.put(0, UnsignedByte(4))
        cpu.execute(UnsignedByte(0xF0))
        assertEquals(5, cpu.pc.value)
    }

    @Test
    fun `0xF0 branch on equal (backwards 4 bytes)`() {
        cpu.zeroFlag = true
        cpu.pc.value = 10
        cpu.mmu.put(10, UnsignedByte(251))
        cpu.execute(UnsignedByte(0xF0))
        assertEquals(6, cpu.pc.value)
    }

    /**
     * BRK - Break Operation
     */

    @Test
    fun `0x00 break execution`() {
        cpu.execute(UnsignedByte(0x00))
        assertTrue(cpu.breakFlag)
    }

    /**
     * CMP - Compare with Accumulator
     */

    @Test
    fun `0xC9 cmp 1 with 1 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xC9))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xC5 cmp 1 with 1 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xC5))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xD5 cmp 1 with 1 zeropage,X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xD5))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xCD cmp 1 with 1 absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xCD))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xDD cmp 1 with 1 absolute, X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xDD))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xD9 cmp 1 with 1 absolute, Y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xD9))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xC1 cmp 1 with 1 ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xC1))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xD1 cmp 1 with 1 ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xD1))
        assertTrue(cpu.zeroFlag)
    }

    /**
     * CPX - Compare X
     */

    @Test
    fun `0xE0 cpx 1 with 1 immediate`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE0))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xE4 cpx 1 with 1 zeropage`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE4))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xEC cpx 1 with 1 absolute`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xEC))
        assertTrue(cpu.zeroFlag)
    }

    /**
     * CPY - Compare Y
     */

    @Test
    fun `0xC0 cpy 1 with 1 immediate`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xC0))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xC4 cpy 1 with 1 zeropage`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xC4))
        assertTrue(cpu.zeroFlag)
    }

    @Test
    fun `0xCC cpy 1 with 1 absolute`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xCC))
        assertTrue(cpu.zeroFlag)
    }

    /**
     * DEC - Decrement Memory
     */

    @Test
    fun `0xC6 dec 5-1 zeropage`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xC6))
        assertEquals(4, cpu.mmu.at(1).value)
    }

    @Test
    fun `0xD6 dec 5-1 zeropage,X`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xD6))
        assertEquals(4, cpu.mmu.at(2).value)
    }

    @Test
    fun `0xCE dec 5-1 absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xCE))
        assertEquals(4, cpu.mmu.at(0xABCD).value)
    }

    @Test
    fun `0xDE dec 5-1 absolute,X`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xDE))
        assertEquals(4, cpu.mmu.at(0xABCE).value)
    }

    /**
     * EOR - Exclusive OR Operations
     */

    @Test
    fun `0x49 1 eor 1 = 0 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x49))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x45 1 eor 1 = 0 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x45))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x55 1 eor 1 = 0 zeropage,X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x55))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x4D 1 eor 1 = 0 absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x4D))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x5D 1 eor 1 = 0 absolute,X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x5D))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x59 1 eor 1 = 0 absolute,Y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x59))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x41 1 eor 1 = 0 ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x41))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0x51 1 eor 1 = 0 ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x51))
        assertEquals(0, cpu.a.value)
    }

    /**
     * Flag (Processor Status) Instructions
     */

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

    /**
     * INC - Increment Memory
     */

    @Test
    fun `0xE6 inc 5+1 zeropage`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xE6))
        assertEquals(6, cpu.mmu.at(1).value)
    }

    @Test
    fun `0xF6 inc 5+1 zeropage,X`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xF6))
        assertEquals(6, cpu.mmu.at(2).value)
    }

    @Test
    fun `0xEE inc 5+1 absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xEE))
        assertEquals(6, cpu.mmu.at(0xABCD).value)
    }

    @Test
    fun `0xFE inc 5+1 absolute,X`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(5))
        cpu.execute(UnsignedByte(0xFE))
        assertEquals(6, cpu.mmu.at(0xABCE).value)
    }

    /**
     * JMP - Jump to Address
     */

    @Test
    fun `0x4C jmp absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x4C))
        assertEquals(0xABCD, cpu.pc.value)
    }

    @Test
    fun `0x6C jmp (indirect)`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(0xCE))
        cpu.mmu.put(0xABCE, UnsignedByte(0xFA))
        cpu.execute(UnsignedByte(0x6C))
        assertEquals(0xFACE, cpu.pc.value)
    }

    /**
     * JSR - Jump Subroutine
     */

    /**
     * LDA - Load Accumulator
     */

    @Test
    fun `0xA9 lda immediate`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA9))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xA5 lda zeropage`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA5))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xB5 lda zeropage,X`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xB5))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xAD lda absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xAD))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xBD lda absolute,X`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xBD))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xB9 lda absolute,Y`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xB9))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xA1 lda ($09, X) indirect x`() {
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA1))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0xB1 lda ($0A),Y indirect y`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xB1))
        assertEquals(1, cpu.a.value)
    }

    /**
     * LDX - Load X Register
     */

    @Test
    fun `0xA2 ldx immediate`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA2))
        assertEquals(1, cpu.x.value)
    }

    @Test
    fun `0xA6 ldx zeropage`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA6))
        assertEquals(1, cpu.x.value)
    }

    @Test
    fun `0xB6 ldx zeropage,Y`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xB6))
        assertEquals(1, cpu.x.value)
    }

    @Test
    fun `0xAE ldx absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xAE))
        assertEquals(1, cpu.x.value)
    }

    @Test
    fun `0xBE ldx absolute,Y`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xBE))
        assertEquals(1, cpu.x.value)
    }

    /**
     * LDY - Load Y Register
     */

    @Test
    fun `0xA0 ldy immediate`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA0))
        assertEquals(1, cpu.y.value)
    }

    @Test
    fun `0xA4 ldy zeropage`() {
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xA4))
        assertEquals(1, cpu.y.value)
    }

    @Test
    fun `0xB4 ldy zeropage,X`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xB4))
        assertEquals(1, cpu.y.value)
    }

    @Test
    fun `0xAC ldy absolute`() {
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xAC))
        assertEquals(1, cpu.y.value)
    }

    @Test
    fun `0xBC ldy absolute,X`() {
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xBC))
        assertEquals(1, cpu.y.value)
    }

    /**
     * LSR - Logical Shift Right
     */

    /**
     * NOP - No Operation
     */

    @Test
    fun `0xEA nop`() {
        cpu.execute(UnsignedByte(0xEA))
        assertTrue(true)
    }

    /**
     * ORA - Logical OR with Accumulator
     */

    @Test
    fun `0x09 1 ora 0 = 1 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x09))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x05 1 ora 0 = 1 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(1, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x05))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x15 1 ora 0 = 1 zeropage,X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.mmu.put(2, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x15))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x0D 1 ora 0 = 1 absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x0D))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x1D 1 ora 0 = 1 absolute,X`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x1D))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x19 1 ora 0 = 1 absolute,Y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x19))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x01 1 ora 0 = 1 ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x01))
        assertEquals(1, cpu.a.value)
    }

    @Test
    fun `0x11 1 ora 0 = 1 ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x11))
        assertEquals(1, cpu.a.value)
    }

    /**
     * Register Instructions
     */

    @Test
    fun `0xAA tax`() {
        cpu.a.value = 0x0A
        cpu.x.value = 0
        cpu.execute(UnsignedByte(0xAA))
        assertEquals(0x0A, cpu.x.value)
    }

    @Test
    fun `0x8A txa`() {
        cpu.a.value = 0
        cpu.x.value = 0x0A
        cpu.execute(UnsignedByte(0x8A))
        assertEquals(0x0A, cpu.a.value)
    }

    @Test
    fun `0xCA dex`() {
        cpu.x.value = 2
        cpu.execute(UnsignedByte(0xCA))
        assertEquals(1, cpu.x.value)
    }

    @Test
    fun `0xE8 inx`() {
        cpu.x.value = 1
        cpu.execute(UnsignedByte(0xE8))
        assertEquals(2, cpu.x.value)
    }

    @Test
    fun `0xA8 tay`() {
        cpu.a.value = 0x0A
        cpu.execute(UnsignedByte(0xA8))
        assertEquals(0x0A, cpu.y.value)
    }

    @Test
    fun `0x98 tya`() {
        cpu.y.value = 0x0A
        cpu.execute(UnsignedByte(0x98))
        assertEquals(0x0A, cpu.a.value)
    }

    @Test
    fun `0x88 dey`() {
        cpu.y.value = 2
        cpu.execute(UnsignedByte(0x88))
        assertEquals(1, cpu.y.value)
    }

    @Test
    fun `0xC8 iny`() {
        cpu.y.value = 1
        cpu.execute(UnsignedByte(0xC8))
        assertEquals(2, cpu.y.value)
    }

    /**
     * ROL - Rotate Left
     */

    /**
     * ROR - Rotate Right
     */

    /**
     * RTI - Return from Interrupt
     */

    /**
     * RTS - Return from Subroutine
     */

    /**
     * SBC - Subtract with Carry
     */

    @Test
    fun `0xE9 sbc 1 - 1 immediate`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE9))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xE9 sbc 2 - 1 - carry immediate`() {
        cpu.a.value = 2
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE9))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xE5 sbc 1 - $00 zeropage`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE5))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xE5 sbc 2 - $00 - carry zeropage`() {
        cpu.a.value = 2
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE5))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xF5 sbc 1 - $00,X zeropage`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0B, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xF5))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xF5 sbc 1 - $00,X - carry zeropage`() {
        cpu.a.value = 2
        cpu.x.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0B, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xF5))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xED sbc 1 - $ABCD absolute`() {
        cpu.a.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xED))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xED sbc 2 - $ABCD - carry absolute`() {
        cpu.a.value = 2
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xED))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xFD sbc 1 - $ABCD,X absolute x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xFD))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xFD sbc 2 - $ABCD,X - carry absolute x`() {
        cpu.a.value = 2
        cpu.x.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xFD))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xF9 sbc 1 - $ABCD,Y absolute y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xF9))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xF9 sbc 2 - $ABCD,Y - carry absolute y`() {
        cpu.a.value = 2
        cpu.y.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xF9))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xE1 sbc 1 - ($09, X) indirect x`() {
        cpu.a.value = 1
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE1))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xE1 sbc 2 - ($09, X) - carry indirect x`() {
        cpu.a.value = 2
        cpu.x.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCD, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xE1))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xF1 sbc 1 - ($0A),Y indirect y`() {
        cpu.a.value = 1
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xF1))
        assertEquals(0, cpu.a.value)
    }

    @Test
    fun `0xF1 sbc 2 - ($0A),Y - carry indirect y`() {
        cpu.a.value = 2
        cpu.y.value = 1
        cpu.carryFlag = true
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.mmu.put(0xABCE, UnsignedByte(1))
        cpu.execute(UnsignedByte(0xF1))
        assertEquals(0, cpu.a.value)
    }

    /**
     * STA - Store Accumulator
     */

    @Test
    fun `0x85 sta zeropage`() {
        cpu.a.value = 0x0A
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x85))
        assertEquals(0x0A, cpu.mmu.at(1).value)
    }

    @Test
    fun `0x95 sta zeropage,X`() {
        cpu.a.value = 0x0A
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x95))
        assertEquals(0x0A, cpu.mmu.at(2).value)
    }

    @Test
    fun `0x8D sta absolute`() {
        cpu.a.value = 0x0A
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x8D))
        assertEquals(0x0A, cpu.mmu.at(0xABCD).value)
    }

    @Test
    fun `0x9D sta absolute,X`() {
        cpu.a.value = 0x0A
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x9D))
        assertEquals(0x0A, cpu.mmu.at(0xABCE).value)
    }

    @Test
    fun `0x99 sta absolute,Y`() {
        cpu.a.value = 0x0A
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x99))
        assertEquals(0x0A, cpu.mmu.at(0xABCE).value)
    }

    @Test
    fun `0x81 sta ($09, X) indirect x`() {
        cpu.a.value = 0x0A
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(0x09))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x81))
        assertEquals(0x0A, cpu.mmu.at(0xABCD).value)
    }

    @Test
    fun `0x91 sta ($0A),Y indirect y`() {
        cpu.a.value = 0x0A
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(0x0A))
        cpu.mmu.put(0x0A, UnsignedByte(0xCD))
        cpu.mmu.put(0x0B, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x91))
        assertEquals(0x0A, cpu.mmu.at(0xABCE).value)
    }

    /**
     * Stack Instructions
     */

    /**
     * STX - Store X Register
     */

    @Test
    fun `0x86 stx zeropage`() {
        cpu.x.value = 0x0A
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x86))
        assertEquals(0x0A, cpu.mmu.at(1).value)
    }

    @Test
    fun `0x96 stx zeropage,Y`() {
        cpu.x.value = 0x0A
        cpu.y.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x96))
        assertEquals(0x0A, cpu.mmu.at(2).value)
    }

    @Test
    fun `0x8E stx absolute`() {
        cpu.x.value = 0x0A
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x8E))
        assertEquals(0x0A, cpu.mmu.at(0xABCD).value)
    }

    /**
     * STY - Store Y Register
     */

    @Test
    fun `0x84 sty zeropage`() {
        cpu.y.value = 0x0A
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x84))
        assertEquals(0x0A, cpu.mmu.at(1).value)
    }

    @Test
    fun `0x94 sty zeropage,X`() {
        cpu.y.value = 0x0A
        cpu.x.value = 1
        cpu.mmu.put(0, UnsignedByte(1))
        cpu.execute(UnsignedByte(0x94))
        assertEquals(0x0A, cpu.mmu.at(2).value)
    }

    @Test
    fun `0x8C sty absolute`() {
        cpu.y.value = 0x0A
        cpu.mmu.put(0, UnsignedByte(0xCD))
        cpu.mmu.put(1, UnsignedByte(0xAB))
        cpu.execute(UnsignedByte(0x8C))
        assertEquals(0x0A, cpu.mmu.at(0xABCD).value)
    }
}