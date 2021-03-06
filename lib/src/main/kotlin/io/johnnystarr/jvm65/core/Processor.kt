/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Processor Interface to allow extensions
 */
interface Processor {
    fun execute(opcode: UnsignedByte)
    fun status(): Register
    fun fetch(): Register
    fun fetchWord(): Register
    fun fetchWordIndirect(useX: Boolean): Register
    fun decodeAddressModes(mode: AddressMode, instruction: String): UnsignedByte
    fun decodeCPAddressModes(mode: AddressMode, instruction: String): UnsignedByte
    fun peek(): Register
    fun bigEndian(msb: UnsignedByte, lsb: UnsignedByte): UnsignedWord
    fun littleEndian(lsb: UnsignedByte, msb: UnsignedByte): UnsignedWord
    fun splitWord(word: UnsignedWord): List<UnsignedByte>
    fun updateFlags(byte: UnsignedByte, checkOverflow: Boolean)
    fun updateFlags(byte: UnsignedByte)
    fun branch(offset: UnsignedByte)
    fun carryToInt(): Int
}