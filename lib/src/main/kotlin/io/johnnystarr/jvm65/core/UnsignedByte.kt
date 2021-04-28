/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

data class UnsignedByte(var value: Int, var state: RegisterState = RegisterState.NONE) : Register {

    override operator fun plus(increment: Int): UnsignedByte {
        val newValue = value + increment
        return when {
            newValue == 0 -> UnsignedByte(0, RegisterState.ZEROED)
            newValue == 256 -> UnsignedByte(0, RegisterState.ZEROED)
            newValue > 256  -> UnsignedByte((newValue - 256), RegisterState.POSITIVE_WRAPAROUND)
            else -> UnsignedByte(newValue)
        }
    }

    override operator fun minus(decrement: Int): UnsignedByte {
        val newValue = value - decrement
        return when {
            newValue == 0 -> UnsignedByte(0, RegisterState.ZEROED)
            newValue == -1 -> UnsignedByte(0xFF, RegisterState.NEGATIVE_WRAPAROUND)
            newValue < -1 -> UnsignedByte((newValue + 256), RegisterState.NEGATIVE_WRAPAROUND)
            else -> UnsignedByte(newValue)
        }
    }

    override fun shiftLeft() {
        this.value = this.value shl 1
        if (this.value == 0) this.state = RegisterState.ZEROED
    }

    override fun shiftRight() {
        this.value = this.value ushr 1
        if (this.value == 0) this.state = RegisterState.ZEROED
    }

    override fun clear() {
        this.value = 0
        this.state = RegisterState.ZEROED
    }
}