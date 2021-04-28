/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * 6502 Processor Class
 *
 * @constructor Creates a P6502 Object
 */
class P6502() : Processor {
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
    var mmu = MemoryManager(0xFFFF, this)

    // stack
    var stack = EightBitStack()

    /**
     * Execute an instruction
     * @return [Boolean] WIP
     * TODO: update this method to perform instructions
     */
    override fun execute(): Boolean {
       return false
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
}