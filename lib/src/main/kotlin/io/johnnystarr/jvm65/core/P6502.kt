/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

import java.lang.Exception

/**
 * 6502 Processor Class
 *
 * @constructor Creates a P6502 Object
 */
class P6502() : Processor, InstructionSet {
    var a = UnsignedByte(0)  // A Register
    var x = UnsignedByte(0)  // X Register
    var y = UnsignedByte(0)  // Y Register
    var sr = UnsignedByte(0) // Status Register
    var pc = UnsignedWord(0) // Program Counter
    var sp = UnsignedWord(0) // Stack Pointer

    // flags
    var carryFlag = false
    var zeroFlag = false
    var interruptDisableFlag = false
    var decimalFlag = false
    var overflowFlag = false
    var negativeFlag = false

    // memory manager
    var mmu = MMU(0xFFFF, this)

    // stack
    var stack = EightBitStack()

    // if we're keeping track of cycles
    var cycles = 0

    /**
     * Execute an instruction
     * @return [Boolean] WIP
     */
    override fun execute(opcode: UnsignedByte) {
        when (opcode.value) {
            // ADC
            0x69 -> adc(AddressMode.IMMEDIATE)
            0x65 -> adc(AddressMode.ZEROPAGE)
            0x75 -> adc(AddressMode.ZEROPAGE_X)
            0x6D -> adc(AddressMode.ABSOLUTE)
            0x7D -> adc(AddressMode.ABSOLUTE_X)
            0x79 -> adc(AddressMode.ABSOLUTE_Y)
            0x61 -> adc(AddressMode.INDIRECT_X)
            0x71 -> adc(AddressMode.INDIRECT_Y)
            // AND
            0x29 -> and(AddressMode.IMMEDIATE)
            0x25 -> and(AddressMode.ZEROPAGE)
            0x35 -> and(AddressMode.ZEROPAGE_X)
            0x2D -> and(AddressMode.ABSOLUTE)
            0x3D -> and(AddressMode.ABSOLUTE_X)
            0x39 -> and(AddressMode.ABSOLUTE_Y)
            0x21 -> and(AddressMode.INDIRECT_X)
            0x31 -> and(AddressMode.INDIRECT_Y)
            // ASL
        }
    }

    /**
     * Sets and returns the status register based on flags
     * @return [UnsignedByte] an encoded byte based on flags
     */
    override fun status(): UnsignedByte {
        val s = UnsignedByte(0)
        if (carryFlag) s.value = s.value or 0b00000001
        if (zeroFlag) s.value = s.value or 0b00000010
        if (interruptDisableFlag) s.value = s.value or 0b00000100
        if (decimalFlag) s.value = s.value or 0b00001000
        if (overflowFlag) s.value = s.value or 0b01000000
        if (negativeFlag) s.value = s.value or 0b10000000
        sr = s
        return s
    }

    /**
     * Step the processor by ond fetch the current byte
     * @return [UnsignedByte] the byte the PC is pointing to before step
     */
    override fun fetch(): UnsignedByte {
        val current = this.mmu.at(this.pc.value)
        this.pc += 1
        return current
    }

    /**
     * Fetch two bytes from memory into one word
     * @return [UnsignedWord] big endian word of both bytes
     */
    override fun fetchWord(): UnsignedWord {
        val msb = fetch()
        val lsb = fetch()
        return bigEndian(msb, lsb)
    }

    /**
     * Fetch two bytes from memory indirectly using a pointer
     * @param index [Int] typically 0 unless X indexing is used
     * @return [UnsignedWord] big endian word of both bytes
     */
    override fun fetchWordIndirect(useX: Boolean): UnsignedWord {
        val xIndex = if (useX) this.x.value else 0
        val pointer = fetch() + xIndex
        val msb = mmu.at(pointer.value)
        val lsb = mmu.at(pointer.value + 1)
        return bigEndian(msb, lsb)
    }

    /**
     * Peek at the next byte in memory
     * @return [UnsignedByte] the byte that is next in line for fetching
     */
    override fun peek(): UnsignedByte {
        return this.mmu.at(this.pc.value + 1)
    }

    /**
     * Transposes two bytes into little endian 16-bit number
     * @param msb [UnsignedByte] most significant byte
     * @param lsb [UnsignedByte] least significant byte
     * @return [UnsignedWord] transposed combination of bytes
     */
    override fun bigEndian(msb: UnsignedByte, lsb: UnsignedByte): UnsignedWord {
        return littleEndian(msb, lsb)
    }

    /**
     * Transposes two bytes into little endian 16-bit number
     * @param lsb [UnsignedByte] least significant byte
     * @param msb [UnsignedByte] most significant byte
     * @return [UnsignedWord] transposed combination of bytes
     */
    override fun littleEndian(lsb: UnsignedByte, msb: UnsignedByte): UnsignedWord {
        return (UnsignedWord((msb.value shl 8) or lsb.value))
    }

    /**
     * Takes a word and splits it into two bytes
     * @param word [UnsignedWord] word to be split
     * @return [List] split word in bytes, MSB first
     */
    override fun splitWord(word: UnsignedWord): List<UnsignedByte> {
        val lsb = UnsignedByte(word.value and 0xFF)
        val msb = UnsignedByte((word.value and 0xFF00) shr 8)
        return listOf(msb, lsb)
    }

    /**
     * ADC (ADd with Carry)
     *  @param mode [AddressMode] the current contextual address mode
     */
    override fun adc(mode: AddressMode) {
        val carry = if (carryFlag) 1 else 0
        a += when (mode) {
            AddressMode.IMMEDIATE  -> mmu.immediate().value + carry
            AddressMode.ZEROPAGE   -> mmu.zeroPage().value + carry
            AddressMode.ZEROPAGE_X -> mmu.zeroPageX().value + carry
            AddressMode.ABSOLUTE   -> mmu.absolute().value + carry
            AddressMode.ABSOLUTE_X -> mmu.absoluteX().value + carry
            AddressMode.ABSOLUTE_Y -> mmu.absoluteY().value + carry
            AddressMode.INDIRECT_Y -> mmu.indirectY().value + carry
            else -> throw IllegalStateException("Mode $mode does not exist.")
        }
    }

    /**
     * And
     */
    override fun and(mode: AddressMode) {
        // implement
    }

    /**
     * Arithmetic shift left
     */
    override fun asl(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on carry clear
     */
    override fun bcc(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on carry set
     */
    override fun bcs(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on equal
     */
    override fun beq(mode: AddressMode) {
        // implement
    }

    /**
     * Bit test
     */
    override fun bit(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on minus
     */
    override fun bmi(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on not equal
     */
    override fun bne(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on plus
     */
    override fun bpl(mode: AddressMode) {
        // implement
    }

    /**
     * Break / interrupt
     */
    override fun brk(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on overflow clear
     */
    override fun bvc(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on overflow set
     */
    override fun bvs(mode: AddressMode) {
        // implement
    }

    /**
     * Clear carry
     */
    override fun clc(mode: AddressMode) {
        // implement
    }

    /**
     * Clear decimal
     */
    override fun cld(mode: AddressMode) {
        // implement
    }

    /**
     * Clear interrupt disable
     */
    override fun cli(mode: AddressMode) {
        // implement
    }

    /**
     * Clear overflow
     */
    override fun clv(mode: AddressMode) {
        // implement
    }

    /**
     * Compare with accumulator
     */
    override fun cmp(mode: AddressMode) {
        // implement
    }

    /**
     * Compare with X
     */
    override fun cpx(mode: AddressMode) {
        // implement
    }

    /**
     * Compare with Y
     */
    override fun cpy(mode: AddressMode) {
        // implement
    }

    /**
     * Decrement
     */
    override fun dec(mode: AddressMode) {
        // implement
    }

    /**
     * Decrement X
     */
    override fun dex(mode: AddressMode) {
        // implement
    }

    /**
     * Decrement Y
     */
    override fun dey(mode: AddressMode) {
        // implement
    }

    /**
     * Exclusive or
     */
    override fun eor(mode: AddressMode) {
        // implement
    }

    /**
     * Increment
     */
    override fun inc(mode: AddressMode) {
        // implement
    }

    /**
     * Increment X
     */
    override fun inx(mode: AddressMode) {
        // implement
    }

    /**
     * Increment Y
     */
    override fun iny(mode: AddressMode) {
        // implement
    }

    /**
     * Jump
     */
    override fun jmp(mode: AddressMode) {
        // implement
    }

    /**
     * Jump subroutine
     */
    override fun jsr(mode: AddressMode) {
        // implement
    }

    /**
     * Load accumulator
     */
    override fun lda(mode: AddressMode) {
        // implement
    }

    /**
     * Load X
     */
    override fun ldx(mode: AddressMode) {
        // implement
    }

    /**
     * Load Y
     */
    override fun ldy(mode: AddressMode) {
        // implement
    }

    /**
     * Logical shift right
     */
    override fun lsr(mode: AddressMode) {
        // implement
    }

    /**
     * No operation
     */
    override fun nop(mode: AddressMode) {
        // implement
    }

    /**
     * Or with accumulator
     */
    override fun ora(mode: AddressMode) {
        // implement
    }

    /**
     * Push accumulator onto stack
     */
    override fun pha(mode: AddressMode) {
        // implement
    }

    /**
     * Push processor status onto stack
     */
    override fun php(mode: AddressMode) {
        // implement
    }

    /**
     * Pull accumulator from stack
     */
    override fun pla(mode: AddressMode) {
        // implement
    }

    /**
     * Pull processor status from stack
     */
    override fun plp(mode: AddressMode) {
        // implement
    }

    /**
     * Rotate left
     */
    override fun rol(mode: AddressMode) {
        // implement
    }

    /**
     * Rotate right
     */
    override fun ror(mode: AddressMode) {
        // implement
    }

    /**
     * Return from interrupt
     */
    override fun rti(mode: AddressMode) {
        // implement
    }

    /**
     * Return from subroutine
     */
    override fun rts(mode: AddressMode) {
        // implement
    }

    /**
     * Subtract with carry
     */
    override fun sbc(mode: AddressMode) {
        // implement
    }

    /**
     * Set carry
     */
    override fun sec(mode: AddressMode) {
        // implement
    }

    /**
     * Set decimal
     */
    override fun sed(mode: AddressMode) {
        // implement
    }

    /**
     * Set interrupt disable
     */
    override fun sei(mode: AddressMode) {
        // implement
    }

    /**
     * Store accumulator
     */
    override fun sta(mode: AddressMode) {
        // implement
    }

    /**
     * Store X
     */
    override fun stx(mode: AddressMode) {
        // implement
    }

    /**
     * Store Y
     */
    override fun sty(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer accumulator to X
     */
    override fun tax(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer accumulator to Y
     */
    override fun tay(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer stack pointer to X
     */
    override fun tsx(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer X to accumulator
     */
    override fun txa(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer X to stack pointer
     */
    override fun txs(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer Y to accumulator
     */
    override fun tya(mode: AddressMode) {
        // implement
    }
}