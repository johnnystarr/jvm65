package io.johnnystarr.jvm65.core

class MemoryManager(size: Int, cpu: Processor? = null) : MMU {
    override var memory: HashMap<Int, UnsignedByte> = HashMap()

    init {
        for (i in 0..size) {
            this.memory[i] = UnsignedByte(0)
        }
    }

    override fun at(index: Int): UnsignedByte? {
        return this.memory[index]
    }
}