/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Class for MMU
 *
 * @constructor Creates a new MMU
 * @param size [Int] The total size of memory in bytes
 * @param cpu [P6502] A reference to the Processor
 */
class MMU(size: Int, var cpu: P6502) : MemoryManager {
    override var memory: HashMap<Int, UnsignedByte> = HashMap()

    init {
        for (i in 0..size) {
            this.memory[i] = UnsignedByte(0)
        }
    }

    /**
     * Retrieve a byte from a memory address
     * @param index [Int] the index/offset address
     * @return [UnsignedByte] returns the byte at this address
     */
    override fun at(index: Int): UnsignedByte? {
        return this.memory[index]
    }

    /**
     * Retrieve a byte from a memory address + the X register
     * @param index [Int] the index/offset address
     * @return [UnsignedByte] returns the byte at this address
     */
    override fun atX(index: Int): UnsignedByte? {
        val x = this.cpu.x.value
        return this.at(index + x)
    }

    /**
     * Retrieve a byte from a memory address + the Y register
     * @param index [Int] the index/offset address
     * @return [UnsignedByte] returns the byte at this address
     */
    override fun atY(index: Int): UnsignedByte? {
        val y = this.cpu.y.value
        return this.at(index + y)
    }

    /**
     * Insert a byte into a memory address
     * @param address [Int] the absolute memory address
     * @param byte [Register] the element to put in memory
     * @return [Unit]
     */
    override fun put(address: Int, byte: Register) {
        if (byte is UnsignedByte)
            this.memory[address] = byte
    }
}