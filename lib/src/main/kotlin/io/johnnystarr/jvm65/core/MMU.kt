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
     * @throws IllegalStateException when element missing
     */
    override fun at(index: Int): UnsignedByte {
        return memory[index] ?: throw IllegalStateException("No memory address element at $index")
    }

    /**
     * Retrieve a byte from a memory address + the X register
     * @param index [Int] the index/offset address
     * @return [UnsignedByte] returns the byte at this address
     */
    override fun atX(index: Int): UnsignedByte {
        val x = this.cpu.x.value
        return at(index + x)
    }

    /**
     * Retrieve a byte from a memory address + the Y register
     * @param index [Int] the index/offset address
     * @return [UnsignedByte] returns the byte at this address
     */
    override fun atY(index: Int): UnsignedByte {
        val y = this.cpu.y.value
        return at(index + y)
    }

    /**
     * Insert a byte into a memory address
     * @param address [Int] the absolute memory address
     * @param byte [Register] the element to put in memory
     * @return [Unit]
     */
    override fun put(address: Int, byte: Register) {
        if (byte is UnsignedByte)
            memory[address] = byte
    }

    /**
     * Retrieves a literal byte from memory
     * @return [UnsignedByte] byte from memory
     */
    override fun immediate(): UnsignedByte {
        return cpu.fetch()
    }

    /**
     * Retrieves byte at zeropage address
     * @return [UnsignedByte] byte from zeropage address
     */
    override fun zeroPage(): UnsignedByte {
        return at(cpu.fetch().value)
    }

    /**
     * Retrieves byte at zeropage address offset by X
     * @return [UnsignedByte] byte from zeropage address offset by x
     */
    override fun zeroPageX(): UnsignedByte {
        return atX(cpu.fetch().value)
    }

    /**
     * Retrieves byte at zeropage address offset by Y
     * @return [UnsignedByte] byte from zeropage address offset by y
     */
    override fun zeroPageY(): UnsignedByte {
        return atY(cpu.fetch().value)
    }

    /**
     * Retrieves byte at absolute 16-bit address
     * @return [UnsignedByte] byte from absolute 16-bit address
     */
    override fun absolute(): UnsignedByte {
        return at(cpu.fetchWord().value)
    }

    /**
     * Retrieves byte at absolute 16-bit address offset by X
     * @return [UnsignedByte] byte from absolute 16-bit address offset by X
     */
    override fun absoluteX(): UnsignedByte {
        return atX(cpu.fetchWord().value)
    }

    /**
     * Retrieves byte at absolute 16-bit address offset by Y
     * @return [UnsignedByte] byte from absolute 16-bit address offset by Y
     */
    override fun absoluteY(): UnsignedByte{
        return atY(cpu.fetchWord().value)
    }

    /**
     * Retrieves 16-bit address indirectly, used for JMP($ABCD)
     * @return [UnsignedWord] indirect word
     */
    override fun indirect(): UnsignedWord {
        val address = cpu.fetchWord().value
        val lsb = at(address)
        val msb = at(address + 1)
        return cpu.littleEndian(lsb, msb)
    }

    /**
     * Retrieves byte indirectly using a zeropage pointer indexed by X
     * @return [UnsignedByte] byte from indirect 16-bit address indexed by X
     */
    override fun indirectX(): UnsignedByte {
        return at(cpu.fetchWordIndirect(true).value)
    }

    /**
     * Retrieves byte indirectly using a zeropage pointer offset by Y
     * @return [UnsignedByte] byte from indirect 16-bit address offset by Y
     */
    override fun indirectY(): UnsignedByte {
        return atY(cpu.fetchWordIndirect(false).value)
    }
}