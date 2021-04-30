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
    fun peek(): Register
    fun littleEndian(lsb: UnsignedByte, msb: UnsignedByte): UnsignedWord
    fun splitWord(word: UnsignedWord): List<UnsignedByte>
}