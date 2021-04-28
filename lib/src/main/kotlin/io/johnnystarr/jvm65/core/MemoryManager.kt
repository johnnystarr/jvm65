package io.johnnystarr.jvm65.core

class MemoryManager(size: Int, var cpu: P6502) : MMU {
    override var memory: HashMap<Int, UnsignedByte> = HashMap()

    init {
        for (i in 0..size) {
            this.memory[i] = UnsignedByte(0)
        }
    }

    override fun at(index: Int): UnsignedByte? {
        return this.memory[index]
    }

    override fun atX(index: Int): UnsignedByte? {
        val x = this.cpu.x.value
        return this.at(index + x)
    }

    override fun atY(index: Int): UnsignedByte? {
        val y = this.cpu.y.value
        return this.at(index + y)
    }

    override fun put(address: Int, byte: Register) {
        if (byte is UnsignedByte)
            this.memory[address] = byte
    }
}