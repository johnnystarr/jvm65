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
            0x0A -> asl(AddressMode.ACCUMULATOR)
            0x06 -> asl(AddressMode.ZEROPAGE)
            0x16 -> asl(AddressMode.ZEROPAGE_X)
            0x0E -> asl(AddressMode.ABSOLUTE)
            0x1E -> asl(AddressMode.ABSOLUTE_X)

            // Flag Operations
            0x18 -> clc(AddressMode.IMPLIED)
            0x38 -> sec(AddressMode.IMPLIED)
            0x58 -> cli(AddressMode.IMPLIED)
            0x78 -> sei(AddressMode.IMPLIED)
            0xB8 -> clv(AddressMode.IMPLIED)
            0xD8 -> cld(AddressMode.IMPLIED)
            0xF8 -> sed(AddressMode.IMPLIED)
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
     * Update flags based on latest byte operation
     * @param byte [UnsignedByte] byte last operated on
     */
    override fun updateFlags(byte: UnsignedByte) {
        (byte.state == RegisterState.ZEROED)
            zeroFlag = true
    }

    /**
     * ADC - (ADd with Carry)
     * @param mode [AddressMode] the current contextual address mode
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
            AddressMode.INDIRECT_X -> mmu.indirectX().value + carry
            AddressMode.INDIRECT_Y -> mmu.indirectY().value + carry
            else -> throw IllegalStateException("ADC mode $mode does not exist.")
        }
        updateFlags(a)
    }

    /**
     * AND - Logical AND Operation
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun and(mode: AddressMode) {
        a = when (mode) {
            AddressMode.IMMEDIATE  -> a.and(mmu.immediate())
            AddressMode.ZEROPAGE   -> a.and(mmu.zeroPage())
            AddressMode.ZEROPAGE_X -> a.and(mmu.zeroPageX())
            AddressMode.ABSOLUTE   -> a.and(mmu.absolute())
            AddressMode.ABSOLUTE_X -> a.and(mmu.absoluteX())
            AddressMode.ABSOLUTE_Y -> a.and(mmu.absoluteY())
            AddressMode.INDIRECT_X -> a.and(mmu.indirectX())
            AddressMode.INDIRECT_Y -> a.and(mmu.indirectY())
            else -> throw IllegalStateException("AND mode $mode does not exist.")
        }
        updateFlags(a)
    }

    /**
     * Arithmetic shift left
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun asl(mode: AddressMode) {
        val byte = when (mode) {
            AddressMode.ACCUMULATOR -> a
            AddressMode.ZEROPAGE    -> mmu.at(mmu.zeroPage().value)
            AddressMode.ZEROPAGE_X  -> mmu.at(mmu.zeroPageX().value)
            AddressMode.ABSOLUTE    -> mmu.absolute()
            AddressMode.ABSOLUTE_X  -> mmu.absoluteX()
            else -> throw IllegalStateException("ASL mode $mode does not exist.")
        }
        byte.shiftLeft()
        updateFlags(byte)
    }

    /**
     * Branch on carry clear
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bcc(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on carry set
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bcs(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on equal
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun beq(mode: AddressMode) {
        // implement
    }

    /**
     * Bit test
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bit(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on minus
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bmi(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on not equal
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bne(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on plus
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bpl(mode: AddressMode) {
        // implement
    }

    /**
     * Break / interrupt
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun brk(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on overflow clear
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bvc(mode: AddressMode) {
        // implement
    }

    /**
     * Branch on overflow set
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bvs(mode: AddressMode) {
        // implement
    }

    /**
     * Clear carry
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun clc(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> carryFlag = false
            else -> throw IllegalStateException("CLC mode $mode does not exist.")
        }
    }

    /**
     * Clear decimal
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cld(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> decimalFlag = false
            else -> throw IllegalStateException("CLD mode $mode does not exist.")
        }
    }

    /**
     * Clear interrupt disable
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cli(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> interruptDisableFlag = false
            else -> throw IllegalStateException("CLI mode $mode does not exist.")
        }
    }

    /**
     * Clear overflow
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun clv(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> overflowFlag = false
            else -> throw IllegalStateException("CLV mode $mode does not exist.")
        }
    }

    /**
     * Compare with accumulator
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cmp(mode: AddressMode) {
        // implement
    }

    /**
     * Compare with X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cpx(mode: AddressMode) {
        // implement
    }

    /**
     * Compare with Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cpy(mode: AddressMode) {
        // implement
    }

    /**
     * Decrement
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun dec(mode: AddressMode) {
        // implement
    }

    /**
     * Decrement X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun dex(mode: AddressMode) {
        // implement
    }

    /**
     * Decrement Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun dey(mode: AddressMode) {
        // implement
    }

    /**
     * Exclusive or
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun eor(mode: AddressMode) {
        // implement
    }

    /**
     * Increment
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun inc(mode: AddressMode) {
        // implement
    }

    /**
     * Increment X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun inx(mode: AddressMode) {
        // implement
    }

    /**
     * Increment Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun iny(mode: AddressMode) {
        // implement
    }

    /**
     * Jump
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun jmp(mode: AddressMode) {
        // implement
    }

    /**
     * Jump subroutine
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun jsr(mode: AddressMode) {
        // implement
    }

    /**
     * Load accumulator
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun lda(mode: AddressMode) {
        // implement
    }

    /**
     * Load X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun ldx(mode: AddressMode) {
        // implement
    }

    /**
     * Load Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun ldy(mode: AddressMode) {
        // implement
    }

    /**
     * Logical shift right
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun lsr(mode: AddressMode) {
        // implement
    }

    /**
     * No operation
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun nop(mode: AddressMode) {
        // implement
    }

    /**
     * Or with accumulator
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun ora(mode: AddressMode) {
        // implement
    }

    /**
     * Push accumulator onto stack
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun pha(mode: AddressMode) {
        // implement
    }

    /**
     * Push processor status onto stack
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun php(mode: AddressMode) {
        // implement
    }

    /**
     * Pull accumulator from stack
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun pla(mode: AddressMode) {
        // implement
    }

    /**
     * Pull processor status from stack
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun plp(mode: AddressMode) {
        // implement
    }

    /**
     * Rotate left
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun rol(mode: AddressMode) {
        // implement
    }

    /**
     * Rotate right
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun ror(mode: AddressMode) {
        // implement
    }

    /**
     * Return from interrupt
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun rti(mode: AddressMode) {
        // implement
    }

    /**
     * Return from subroutine
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun rts(mode: AddressMode) {
        // implement
    }

    /**
     * Subtract with carry
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun sbc(mode: AddressMode) {
        // implement
    }

    /**
     * Set carry
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun sec(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> carryFlag = true
            else -> throw IllegalStateException("SEC mode $mode does not exist.")
        }
    }

    /**
     * Set decimal
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun sed(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> decimalFlag = true
            else -> throw IllegalStateException("SED mode $mode does not exist.")
        }
    }

    /**
     * Set interrupt disable
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun sei(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> interruptDisableFlag = true
            else -> throw IllegalStateException("SEC mode $mode does not exist.")
        }
    }

    /**
     * Store accumulator
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun sta(mode: AddressMode) {
        // implement
    }

    /**
     * Store X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun stx(mode: AddressMode) {
        // implement
    }

    /**
     * Store Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun sty(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer accumulator to X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun tax(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer accumulator to Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun tay(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer stack pointer to X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun tsx(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer X to accumulator
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun txa(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer X to stack pointer
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun txs(mode: AddressMode) {
        // implement
    }

    /**
     * Transfer Y to accumulator
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun tya(mode: AddressMode) {
        // implement
    }
}