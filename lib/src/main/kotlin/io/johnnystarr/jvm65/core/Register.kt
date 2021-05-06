/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Register Interface
 */
interface Register {
    fun shiftLeft()
    fun shiftRight()
    fun clear()
    fun and(register: UnsignedRegister): UnsignedByte
    fun or(register: UnsignedRegister): UnsignedByte
    fun xor(register: UnsignedRegister): UnsignedByte
}