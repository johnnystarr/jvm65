/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Custom Class for handling unsigned words
 *
 * @property value [Int] the initial 16-bit value of this word
 * @property state [RegisterState] current state based on various internal operations
 */
data class UnsignedWord(var value: Int, var state: RegisterState = RegisterState.NONE) : Register {

    /**
     * Addition word operation
     * @param increment [Int] the number to add to our existing value
     * @return [UnsignedByte] the result of addition after computation
     */
    override operator fun plus(increment: Int): UnsignedWord {
        val newValue = value + increment
        return when {
            newValue == 0 -> UnsignedWord(0, RegisterState.ZEROED)
            newValue == 65536 -> UnsignedWord(0, RegisterState.ZEROED)
            newValue > 65536  -> UnsignedWord((newValue - 65536), RegisterState.POSITIVE_WRAPAROUND)
            else -> UnsignedWord(newValue)
        }
    }

    /**
     * Subtraction word operation
     * @param decrement [Int] the number to subtract to our existing value
     * @return [UnsignedByte] the result of subtraction after computation
     */
    override operator fun minus(decrement: Int): UnsignedWord {
        val newValue = value - decrement
        return when {
            newValue == 0 -> UnsignedWord(0, RegisterState.ZEROED)
            newValue == -1 -> UnsignedWord(0xFFFF, RegisterState.ZEROED)
            newValue < -1 -> UnsignedWord((newValue + 65536), RegisterState.NEGATIVE_WRAPAROUND)
            else -> UnsignedWord(newValue)
        }
    }

    /**
     * Shift byte left by 1
     * @return [Unit]
     */
    override fun shiftLeft() {
        this.value = this.value shl 1
        if (this.value == 0) this.state = RegisterState.ZEROED
    }

    /**
     * Shift byte right by 1
     * @return [Unit]
     */
    override fun shiftRight() {
        this.value = this.value ushr 1
        if (this.value == 0) this.state = RegisterState.ZEROED
    }

    /**
     * Set this byte value to 0
     * @return [Unit]
     */
    override fun clear() {
        this.value = 0
        this.state = RegisterState.ZEROED
    }
}