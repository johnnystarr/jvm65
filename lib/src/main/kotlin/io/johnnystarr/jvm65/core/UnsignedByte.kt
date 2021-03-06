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
data class UnsignedByte (
    override var value: Int,
    override var state: RegisterState = RegisterState.NONE,
) : UnsignedRegister(value, state), Arithmetic {

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
     * Binary Coded Decimal Addition
     * @param increment [Int] amount to add
     * @param carry [Int] 1 or 0 based on carry flag
     * @return [UnsignedByte] result of addition
     */
    override fun bcdPlus(increment: Int, carry: Int): UnsignedByte {
        return UnsignedByte(0)
    }

    /**
     * Binary Coded Decimal Addition
     * @param decrement [Int] amount to add
     * @param carry [Int] 1 or 0 based on carry flag
     * @return [UnsignedByte] result of subtraction
     */
    override fun bcdMinus(decrement: Int, carry: Int): UnsignedByte {
        return UnsignedByte(0)
    }

    /**
     * Increment register by 1
     * @return [UnsignedByte] incremented value
     */
    override fun inc(): UnsignedByte {
        val result = plus(1)
        value = result.value
        return result
    }

    /**
     * Decrement register by 1
     * @return [UnsignedByte] decremented value
     */
    override fun dec(): UnsignedByte {
        val result = minus(1)
        value = result.value
        return result
    }
}