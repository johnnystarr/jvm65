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
    var breakFlag = false

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
            0x69 -> adc(AddressMode.IMMEDIATE)
            0x65 -> adc(AddressMode.ZEROPAGE)
            0x75 -> adc(AddressMode.ZEROPAGE_X)
            0x6D -> adc(AddressMode.ABSOLUTE)
            0x7D -> adc(AddressMode.ABSOLUTE_X)
            0x79 -> adc(AddressMode.ABSOLUTE_Y)
            0x61 -> adc(AddressMode.INDIRECT_X)
            0x71 -> adc(AddressMode.INDIRECT_Y)
            0x29 -> and(AddressMode.IMMEDIATE)
            0x25 -> and(AddressMode.ZEROPAGE)
            0x35 -> and(AddressMode.ZEROPAGE_X)
            0x2D -> and(AddressMode.ABSOLUTE)
            0x3D -> and(AddressMode.ABSOLUTE_X)
            0x39 -> and(AddressMode.ABSOLUTE_Y)
            0x21 -> and(AddressMode.INDIRECT_X)
            0x31 -> and(AddressMode.INDIRECT_Y)
            0x0A -> asl(AddressMode.ACCUMULATOR)
            0x06 -> asl(AddressMode.ZEROPAGE)
            0x16 -> asl(AddressMode.ZEROPAGE_X)
            0x0E -> asl(AddressMode.ABSOLUTE)
            0x1E -> asl(AddressMode.ABSOLUTE_X)
            0x24 -> bit(AddressMode.ZEROPAGE)
            0x2C -> bit(AddressMode.ABSOLUTE)
            0x10 -> bpl(AddressMode.RELATIVE)
            0x30 -> bmi(AddressMode.RELATIVE)
            0x50 -> bvc(AddressMode.RELATIVE)
            0x70 -> bvs(AddressMode.RELATIVE)
            0x90 -> bcc(AddressMode.RELATIVE)
            0xB0 -> bcs(AddressMode.RELATIVE)
            0xD0 -> bne(AddressMode.RELATIVE)
            0xF0 -> beq(AddressMode.RELATIVE)
            0x00 -> brk(AddressMode.IMPLIED)
            0xC9 -> cmp(AddressMode.IMMEDIATE)
            0xC5 -> cmp(AddressMode.ZEROPAGE)
            0xD5 -> cmp(AddressMode.ZEROPAGE_X)
            0xCD -> cmp(AddressMode.ABSOLUTE)
            0xDD -> cmp(AddressMode.ABSOLUTE_X)
            0xD9 -> cmp(AddressMode.ABSOLUTE_Y)
            0xC1 -> cmp(AddressMode.INDIRECT_X)
            0xD1 -> cmp(AddressMode.INDIRECT_Y)
            0xE0 -> cpx(AddressMode.IMMEDIATE)
            0xE4 -> cpx(AddressMode.ZEROPAGE)
            0xEC -> cpx(AddressMode.ABSOLUTE)
            0xC0 -> cpy(AddressMode.IMMEDIATE)
            0xC4 -> cpy(AddressMode.ZEROPAGE)
            0xCC -> cpy(AddressMode.ABSOLUTE)
            0xC6 -> dec(AddressMode.ZEROPAGE)
            0xD6 -> dec(AddressMode.ZEROPAGE_X)
            0xCE -> dec(AddressMode.ABSOLUTE)
            0xDE -> dec(AddressMode.ABSOLUTE_X)
            0x49 -> eor(AddressMode.IMMEDIATE)
            0x45 -> eor(AddressMode.ZEROPAGE)
            0x55 -> eor(AddressMode.ZEROPAGE_X)
            0x4D -> eor(AddressMode.ABSOLUTE)
            0x5D -> eor(AddressMode.ABSOLUTE_X)
            0x59 -> eor(AddressMode.ABSOLUTE_Y)
            0x41 -> eor(AddressMode.INDIRECT_X)
            0x51 -> eor(AddressMode.INDIRECT_Y)
            0x18 -> clc(AddressMode.IMPLIED)
            0x38 -> sec(AddressMode.IMPLIED)
            0x58 -> cli(AddressMode.IMPLIED)
            0x78 -> sei(AddressMode.IMPLIED)
            0xB8 -> clv(AddressMode.IMPLIED)
            0xD8 -> cld(AddressMode.IMPLIED)
            0xF8 -> sed(AddressMode.IMPLIED)
            0xE6 -> inc(AddressMode.ZEROPAGE)
            0xF6 -> inc(AddressMode.ZEROPAGE_X)
            0xEE -> inc(AddressMode.ABSOLUTE)
            0xFE -> inc(AddressMode.ABSOLUTE_X)
            0x4C -> jmp(AddressMode.ABSOLUTE)
            0x6C -> jmp(AddressMode.INDIRECT)
            0x20 -> jsr(AddressMode.ABSOLUTE)
            0xA9 -> lda(AddressMode.IMMEDIATE)
            0xA5 -> lda(AddressMode.ZEROPAGE)
            0xB5 -> lda(AddressMode.ZEROPAGE_X)
            0xAD -> lda(AddressMode.ABSOLUTE)
            0xBD -> lda(AddressMode.ABSOLUTE_X)
            0xB9 -> lda(AddressMode.ABSOLUTE_Y)
            0xA1 -> lda(AddressMode.INDIRECT_X)
            0xB1 -> lda(AddressMode.INDIRECT_Y)
            0xA2 -> ldx(AddressMode.IMMEDIATE)
            0xA6 -> ldx(AddressMode.ZEROPAGE)
            0xB6 -> ldx(AddressMode.ZEROPAGE_Y)
            0xAE -> ldx(AddressMode.ABSOLUTE)
            0xBE -> ldx(AddressMode.ABSOLUTE_Y)
            0xA0 -> ldy(AddressMode.IMMEDIATE)
            0xA4 -> ldy(AddressMode.ZEROPAGE)
            0xB4 -> ldy(AddressMode.ZEROPAGE_X)
            0xAC -> ldy(AddressMode.ABSOLUTE)
            0xBC -> ldy(AddressMode.ABSOLUTE_X)
            0x4A -> lsr(AddressMode.ACCUMULATOR)
            0x46 -> lsr(AddressMode.ZEROPAGE)
            0x56 -> lsr(AddressMode.ZEROPAGE_X)
            0x4E -> lsr(AddressMode.ABSOLUTE)
            0x5E -> lsr(AddressMode.ABSOLUTE_X)
            0xEA -> nop(AddressMode.IMPLIED)
            0x09 -> ora(AddressMode.IMMEDIATE)
            0x05 -> ora(AddressMode.ZEROPAGE)
            0x15 -> ora(AddressMode.ZEROPAGE_X)
            0x0D -> ora(AddressMode.ABSOLUTE)
            0x1D -> ora(AddressMode.ABSOLUTE_X)
            0x19 -> ora(AddressMode.ABSOLUTE_Y)
            0x01 -> ora(AddressMode.INDIRECT_X)
            0x11 -> ora(AddressMode.INDIRECT_Y)
            0xAA -> tax(AddressMode.IMPLIED)
            0x8A -> txa(AddressMode.IMPLIED)
            0xCA -> dex(AddressMode.IMPLIED)
            0xE8 -> inx(AddressMode.IMPLIED)
            0xA8 -> tay(AddressMode.IMPLIED)
            0x98 -> tya(AddressMode.IMPLIED)
            0x88 -> dey(AddressMode.IMPLIED)
            0xC8 -> iny(AddressMode.IMPLIED)
            0x2A -> rol(AddressMode.ACCUMULATOR)
            0x26 -> rol(AddressMode.ZEROPAGE)
            0x36 -> rol(AddressMode.ZEROPAGE_X)
            0x2E -> rol(AddressMode.ABSOLUTE)
            0x3E -> rol(AddressMode.ABSOLUTE_X)
            0x6A -> ror(AddressMode.ACCUMULATOR)
            0x66 -> ror(AddressMode.ZEROPAGE)
            0x76 -> ror(AddressMode.ZEROPAGE_X)
            0x6E -> ror(AddressMode.ABSOLUTE)
            0x7E -> ror(AddressMode.ABSOLUTE_X)
            0x40 -> rti(AddressMode.IMPLIED)
            0x60 -> rts(AddressMode.IMPLIED)
            0xE9 -> sbc(AddressMode.IMMEDIATE)
            0xE5 -> sbc(AddressMode.ZEROPAGE)
            0xF5 -> sbc(AddressMode.ZEROPAGE_X)
            0xED -> sbc(AddressMode.ABSOLUTE)
            0xFD -> sbc(AddressMode.ABSOLUTE_X)
            0xF9 -> sbc(AddressMode.ABSOLUTE_Y)
            0xE1 -> sbc(AddressMode.INDIRECT_X)
            0xF1 -> sbc(AddressMode.INDIRECT_Y)
            0x85 -> sta(AddressMode.ZEROPAGE)
            0x95 -> sta(AddressMode.ZEROPAGE_X)
            0x8D -> sta(AddressMode.ABSOLUTE)
            0x9D -> sta(AddressMode.ABSOLUTE_X)
            0x99 -> sta(AddressMode.ABSOLUTE_Y)
            0x81 -> sta(AddressMode.INDIRECT_X)
            0x91 -> sta(AddressMode.INDIRECT_Y)
            0x9A -> txs(AddressMode.IMPLIED)
            0xBA -> tsx(AddressMode.IMPLIED)
            0x48 -> pha(AddressMode.IMPLIED)
            0x68 -> pla(AddressMode.IMPLIED)
            0x08 -> php(AddressMode.IMPLIED)
            0x28 -> plp(AddressMode.IMPLIED)
            0x86 -> stx(AddressMode.ZEROPAGE)
            0x96 -> stx(AddressMode.ZEROPAGE_Y)
            0x8E -> stx(AddressMode.ABSOLUTE)
            0x84 -> sty(AddressMode.ZEROPAGE)
            0x94 -> sty(AddressMode.ZEROPAGE_X)
            0x8C -> sty(AddressMode.ABSOLUTE)
            else -> throw IllegalArgumentException("Illegal Opcode: ${opcode.value}")
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
        this.pc.inc()
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
     * Provides byte address lookup based on pirmary address modes
     * @param mode [AddressMode] the mode to decode
     * @param instruction [String] the instruction we are presently running
     */
    override fun decodeAddressModes(mode: AddressMode, instruction: String): UnsignedByte {
        return when(mode) {
            AddressMode.IMMEDIATE  -> mmu.immediate()
            AddressMode.ZEROPAGE   -> mmu.zeroPage()
            AddressMode.ZEROPAGE_X -> mmu.zeroPageX()
            AddressMode.ABSOLUTE   -> mmu.absolute()
            AddressMode.ABSOLUTE_X -> mmu.absoluteX()
            AddressMode.ABSOLUTE_Y -> mmu.absoluteY()
            AddressMode.INDIRECT_X -> mmu.indirectX()
            AddressMode.INDIRECT_Y -> mmu.indirectY()
            else -> throw IllegalStateException("$instruction: mode $mode does not exist.")
        }
    }

    /**
     * Provides byte address lookup based on CP* address modes
     * @param mode [AddressMode] the mode to decode
     * @param instruction [String] the instruction we are presently running
     */
    override fun decodeCPAddressModes(mode: AddressMode, instruction: String): UnsignedByte {
        return when (mode) {
            AddressMode.IMMEDIATE -> mmu.immediate()
            AddressMode.ZEROPAGE -> mmu.zeroPage()
            AddressMode.ABSOLUTE -> mmu.absolute()
            else -> throw IllegalStateException("CPX mode $mode does not exist.")
        }
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
     * @param checkOverflow [Boolean] checks overflow flag for certain operations only
     */
    override fun updateFlags(byte: UnsignedByte, checkOverflow: Boolean) {
        updateFlags(byte)
        if (checkOverflow) {
            overflowFlag = byte.value in 0x80..0xFF || byte.value > 0x7F
        }
    }

    /**
     * Update flags based on latest byte operation
     * @param byte [UnsignedByte] byte last operated on
     */
    override fun updateFlags(byte: UnsignedByte) {
        when (byte.state) {
            RegisterState.NONE -> {}
            RegisterState.ZEROED -> zeroFlag = true
            RegisterState.POSITIVE_WRAPAROUND -> cycles += 1
            RegisterState.NEGATIVE_WRAPAROUND -> cycles += 1
        }
        if ((byte.value and 0x80) == 0x80)
            negativeFlag = true
    }

    /**
     * Branch to an signed offset
     */
    override fun branch(offset: UnsignedByte) {
        if (offset.value < 0x80)
            pc += offset.value
        else
            pc -= (256 - offset.value)
    }

    /**
     * ADC - (ADd with Carry)
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun adc(mode: AddressMode) {
        val carry = if (carryFlag) 1 else 0
        val increment = decodeAddressModes(mode, "ADC").value
        if (!decimalFlag) {
            a += increment + carry
        } else {
            a = a.bcdPlus(increment, carry)
        }
        updateFlags(a, checkOverflow = true)
    }

    /**
     * AND - Logical AND Operation
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun and(mode: AddressMode) {
        val byte = decodeAddressModes(mode, "AND")
        a = a.and(byte)
        updateFlags(a)
    }

    /**
     * Arithmetic shift left
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun asl(mode: AddressMode) {
        val byte = when (mode) {
            AddressMode.ACCUMULATOR -> a
            AddressMode.ZEROPAGE    -> mmu.zeroPage()
            AddressMode.ZEROPAGE_X  -> mmu.zeroPageX()
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
        when (mode) {
            AddressMode.RELATIVE -> if (!carryFlag) branch(fetch())
            else -> throw IllegalStateException("BCC mode $mode does not exist.")
        }
    }

    /**
     * Branch on carry set
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bcs(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (carryFlag) branch(fetch())
            else -> throw IllegalStateException("BCS mode $mode does not exist.")
        }
    }

    /**
     * Branch on equal
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun beq(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (zeroFlag) branch(fetch())
            else -> throw IllegalStateException("BEQ mode $mode does not exist.")
        }
    }

    /**
     * Bit test
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bit(mode: AddressMode) {
        val byte = when (mode) {
            AddressMode.ZEROPAGE -> mmu.zeroPage()
            AddressMode.ABSOLUTE -> mmu.absolute()
            else -> throw IllegalStateException("BIT mode $mode does not exist.")
        }
        val result = (a.value and byte.value)
        // BIT just sets flags
        zeroFlag = (result == 0)
        negativeFlag = ((result and 0x80) == 0x80)
        overflowFlag = ((result and 0x40) == 0x40)
    }

    /**
     * Branch on minus
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bmi(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (negativeFlag) branch(fetch())
            else -> throw IllegalStateException("BMI mode $mode does not exist.")
        }
    }

    /**
     * Branch on not equal
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bne(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (!zeroFlag) branch(fetch())
            else -> throw IllegalStateException("BNE mode $mode does not exist.")
        }
    }

    /**
     * Branch on plus
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bpl(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (!negativeFlag) branch(fetch())
            else -> throw IllegalStateException("BPL mode $mode does not exist.")
        }
    }

    /**
     * Break / interrupt
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun brk(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> breakFlag = true
            else -> throw IllegalStateException("BRK mode $mode does not exist.")
        }
    }

    /**
     * Branch on overflow clear
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bvc(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (!overflowFlag) branch(fetch())
            else -> throw IllegalStateException("BVC mode $mode does not exist.")
        }
    }

    /**
     * Branch on overflow set
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun bvs(mode: AddressMode) {
        when (mode) {
            AddressMode.RELATIVE -> if (overflowFlag) branch(fetch())
            else -> throw IllegalStateException("BVS mode $mode does not exist.")
        }
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
        val byte = decodeAddressModes(mode, "ADC").value
        val result = a - byte
        updateFlags(result, checkOverflow = true)
    }

    /**
     * Compare with X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cpx(mode: AddressMode) {
        val byte = decodeCPAddressModes(mode, "CPX")
        val result = x - byte.value
        updateFlags(result, checkOverflow = true)
    }

    /**
     * Compare with Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun cpy(mode: AddressMode) {
        val byte = decodeCPAddressModes(mode, "CPY")
        val result = y - byte.value
        updateFlags(result, checkOverflow = true)
    }

    /**
     * Decrement
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun dec(mode: AddressMode) {
        val byte = when (mode) {
            AddressMode.ZEROPAGE   -> mmu.zeroPage().dec()
            AddressMode.ZEROPAGE_X -> mmu.zeroPageX().dec()
            AddressMode.ABSOLUTE   -> mmu.absolute().dec()
            AddressMode.ABSOLUTE_X -> mmu.absoluteX().dec()
            else -> throw IllegalStateException("DEC mode $mode does not exist.")
        }
        updateFlags(byte)
    }

    /**
     * Decrement X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun dex(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> x.dec()
            else -> throw IllegalStateException("DEX mode $mode does not exist.")
        }
        updateFlags(x)
    }

    /**
     * Decrement Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun dey(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> y.dec()
            else -> throw IllegalStateException("DEY mode $mode does not exist.")
        }
        updateFlags(y)
    }

    /**
     * Exclusive or
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun eor(mode: AddressMode) {
        val byte = decodeAddressModes(mode, "EOR")
        a = a.eor(byte)
        updateFlags(a)
    }

    /**
     * Increment
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun inc(mode: AddressMode) {
        val byte = when (mode) {
            AddressMode.ZEROPAGE   -> mmu.zeroPage().inc()
            AddressMode.ZEROPAGE_X -> mmu.zeroPageX().inc()
            AddressMode.ABSOLUTE   -> mmu.absolute().inc()
            AddressMode.ABSOLUTE_X -> mmu.absoluteX().inc()
            else -> throw IllegalStateException("INC mode $mode does not exist.")
        }
        updateFlags(byte)
    }

    /**
     * Increment X
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun inx(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> x.inc()
            else -> throw IllegalStateException("INX mode $mode does not exist.")
        }
        updateFlags(x)
    }

    /**
     * Increment Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun iny(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> y.inc()
            else -> throw IllegalStateException("INY mode $mode does not exist.")
        }
        updateFlags(y)
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
        when (mode) {
            AddressMode.IMPLIED -> x.value = a.value
            else -> throw IllegalStateException("TAX mode $mode does not exist.")
        }
        updateFlags(x)
    }

    /**
     * Transfer accumulator to Y
     * @param mode [AddressMode] the current contextual address mode
     */
    override fun tay(mode: AddressMode) {
        when (mode) {
            AddressMode.IMPLIED -> y.value = a.value
            else -> throw IllegalStateException("TAY mode $mode does not exist.")
        }
        updateFlags(y)
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
        when (mode) {
            AddressMode.IMPLIED -> a.value = x.value
            else -> throw IllegalStateException("TXA mode $mode does not exist.")
        }
        updateFlags(a)
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
        when (mode) {
            AddressMode.IMPLIED -> a.value = y.value
            else -> throw IllegalStateException("TYA mode $mode does not exist.")
        }
        updateFlags(a)
    }
}