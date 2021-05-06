/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */
package io.johnnystarr.jvm65.core

/**
 * Arithmetic Interface
 */
interface Arithmetic {
    operator fun plus(increment: Int): UnsignedRegister
    operator fun minus(decrement: Int): UnsignedRegister
    fun bcdPlus(increment: Int, carry: Int): UnsignedRegister
    fun bcdMinus(decrement: Int, carry: Int): UnsignedRegister
    fun inc(): UnsignedRegister
    fun dec(): UnsignedRegister
}