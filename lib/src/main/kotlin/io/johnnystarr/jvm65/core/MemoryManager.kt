/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Memory Management Interface
 */
interface MemoryManager {
    var memory: HashMap<Int, UnsignedByte>

    fun at(index: Int): Register
    fun atX(index: Int): Register
    fun atY(index: Int): Register
    fun put(address: Int, byte: Register)
    fun immediate(): Register
    fun zeroPage(): Register
    fun zeroPageX(): Register
    fun absolute(): Register
    fun absoluteX(): Register
    fun absoluteY(): Register
    fun indirectX(): Register
    fun indirectY(): Register
}