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
}