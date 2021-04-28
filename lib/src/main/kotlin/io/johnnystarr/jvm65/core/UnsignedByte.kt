/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Custom Class for handling unsigned bytes
 *
 * @property value [Int] the initial 8-bit value of this byte
 * @property state [RegisterState] current state based on various internal operations
 */
data class UnsignedByte(var value: Int, var state: RegisterState = RegisterState.NONE) : Register {

    /**
     * Addition byte operation
     * @param increment [Int] the number to add to our existing value
     * @return [UnsignedByte] the result of addition after computation
     */
    override operator fun plus(increment: Int): UnsignedByte {
        val newValue = value + increment
        return when {
            newValue == 0 -> UnsignedByte(0, RegisterState.ZEROED)
            newValue == 256 -> UnsignedByte(0, RegisterState.ZEROED)
            newValue > 256  -> UnsignedByte((newValue - 256), RegisterState.POSITIVE_WRAPAROUND)
            else -> UnsignedByte(newValue)
        }
    }

    /**
     * Subtraction byte operation
     * @param decrement [Int] the number to subtract to our existing value
     * @return [UnsignedByte] the result of subtraction after computation
     */
    override operator fun minus(decrement: Int): UnsignedByte {
        val newValue = value - decrement
        return when {
            newValue == 0 -> UnsignedByte(0, RegisterState.ZEROED)
            newValue == -1 -> UnsignedByte(0xFF, RegisterState.NEGATIVE_WRAPAROUND)
            newValue < -1 -> UnsignedByte((newValue + 256), RegisterState.NEGATIVE_WRAPAROUND)
            else -> UnsignedByte(newValue)
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