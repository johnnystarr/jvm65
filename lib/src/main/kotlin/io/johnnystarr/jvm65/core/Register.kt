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
    fun inc()
    fun dec()
    fun and(register: UnsignedRegister): UnsignedRegister
    fun or(register: UnsignedRegister): UnsignedRegister
    fun xor(register: UnsignedRegister): UnsignedRegister
}