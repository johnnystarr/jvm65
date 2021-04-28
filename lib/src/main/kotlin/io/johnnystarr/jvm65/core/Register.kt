/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

interface Register {
    operator fun plus(increment: Int): Register
    operator fun minus(decrement: Int): Register
    fun shiftLeft()
    fun shiftRight()
    fun clear()
}