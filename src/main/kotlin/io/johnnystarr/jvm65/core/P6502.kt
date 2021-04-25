package io.johnnystarr.jvm65.core

class P6502() : Processor {
    var a = UnsignedByte(0)  // A Register
    var x = UnsignedByte(0)  // X Register
    var y = UnsignedByte(0)  // Y Register
    var sr = UnsignedWord(0) // Status Register
    var pc = UnsignedWord(0) // Program Counter

    // flags
    var carryFlag = false
    var zeroFlag = false
    var interruptDisableFlag = false
    var decimalFlag = false
    var overflowFlag = false
    var negativeFlag = false

    override fun execute(): Boolean {
       return false
    }

    override fun status(): UnsignedByte {
        val s = UnsignedByte(0)
        when {
            this.carryFlag -> s.value = s.value or 0b00000001
            this.zeroFlag  -> s.value = s.value or 0b00000010
            this.interruptDisableFlag -> s.value = s.value or 0b00000100
            this.decimalFlag -> s.value = s.value or 0b00001000
            this.overflowFlag -> s.value = s.value or 0b01000000
            this.negativeFlag -> s.value = s.value or 0b10000000
        }
        return s
    }
}