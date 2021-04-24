package io.johnnystarr.jvm65.core

class M6502() : Processor {
    var a = UnsignedByte(0) // A Register
    var x = UnsignedByte(0) // X Register
    var y = UnsignedByte(0) // Y Register
    var sr: Short = 0  // Status Register
    var pc: Short = 0  // Program Counter

    // flags
    var carryFlag = false
    var zeroFlag = false
    var decimalFlag = false
    var overflowFlag = false
    var negativeFlag = false

    override fun execute(): Boolean {
       return false
    }
}