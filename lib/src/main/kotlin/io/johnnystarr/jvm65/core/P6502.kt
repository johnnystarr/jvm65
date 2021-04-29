/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

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

    /**
     * Execute an instruction
     * @return [Boolean] WIP
     */
    override fun execute(opcode: UnsignedByte) {
        when (opcode.value) {
            0x69 -> adc(AddressMode.IMMEDIATE)
            0x65 -> adc(AddressMode.ZEROPAGE)
            0x75 -> adc(AddressMode.ZEROPAGE_X)
            0x6D -> adc(AddressMode.ABSOLUTE)
            0x7D -> adc(AddressMode.ABSOLUTE_X)
            0x79 -> adc(AddressMode.ABSOLUTE_Y)
            0x61 -> adc(AddressMode.INDIRECT_X)
            0x71 -> adc(AddressMode.INDIRECT_Y)
        }
    }

    /**
     * Sets and returns the status register based on flags
     * @return [UnsignedByte] an encoded byte based on flags
     */
    override fun status(): UnsignedByte {
        val s = UnsignedByte(0)
        if (this.carryFlag) s.value = s.value or 0b00000001
        if (this.zeroFlag) s.value = s.value or 0b00000010
        if (this.interruptDisableFlag) s.value = s.value or 0b00000100
        if (this.decimalFlag) s.value = s.value or 0b00001000
        if (this.overflowFlag) s.value = s.value or 0b01000000
        if (this.negativeFlag) s.value = s.value or 0b10000000
        this.sr = s
        return s
    }

    /**
     * Step the processor by one
     * @return [UnsignedByte] the byte the PC is pointing to before step
     */
    override fun step(): UnsignedByte? {
        val current = this.mmu.at(this.pc.value)
        this.pc += 1
        return current
    }

    /**
     * Peek at the next byte in memory
     * @return [UnsignedByte] the byte that is next in line for fetching
     */
    override fun peek(): UnsignedByte? {
        return this.mmu.at(this.pc.value + 1)
    }

    /**
     * Add with carry
     */
    override fun adc(mode: AddressMode) {
        // implement
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