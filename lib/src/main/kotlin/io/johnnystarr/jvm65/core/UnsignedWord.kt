/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

data class UnsignedWord(var value: Int, var state: RegisterState = RegisterState.NONE) : Register {

    override operator fun plus(increment: Int): UnsignedWord {
        val newValue = value + increment
        return when {
            newValue == 0 -> UnsignedWord(0, RegisterState.ZEROED)
            newValue == 65536 -> UnsignedWord(0, RegisterState.ZEROED)
            newValue > 65536  -> UnsignedWord((newValue - 65536), RegisterState.POSITIVE_WRAPAROUND)
            else -> UnsignedWord(newValue)
        }
    }

    override operator fun minus(decrement: Int): UnsignedWord {
        val newValue = value - decrement
        return when {
            newValue == 0 -> UnsignedWord(0, RegisterState.ZEROED)
            newValue == -1 -> UnsignedWord(0xFFFF, RegisterState.ZEROED)
            newValue < -1 -> UnsignedWord((newValue + 65536), RegisterState.NEGATIVE_WRAPAROUND)
            else -> UnsignedWord(newValue)
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