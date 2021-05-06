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
data class UnsignedWord (
    override var value: Int,
    override var state: RegisterState = RegisterState.NONE,
) : UnsignedRegister(value, state), Arithmetic {

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
     * Binary Coded Decimal Addition
     * @param increment [Int] amount to add
     * @param carry [Int] 1 or 0 based on carry flag
     * @return [UnsignedWord] result of addition
     */
    override fun bcdPlus(increment: Int, carry: Int): UnsignedWord {
        return UnsignedWord(0)
    }

    /**
     * Binary Coded Decimal Addition
     * @param decrement [Int] amount to add
     * @param carry [Int] 1 or 0 based on carry flag
     * @return [UnsignedWord] result of subtraction
     */
    override fun bcdMinus(decrement: Int, carry: Int): UnsignedWord {
        return UnsignedWord(0)
    }

    /**
     * Increment register by 1
     * @return [UnsignedWord] incremented value
     */
    override fun inc(): UnsignedWord {
        val result = plus(1)
        value = result.value
        return result
    }

    /**
     * Decrement register by 1
     * @return [UnsignedWord] decremented value
     */
    override fun dec(): UnsignedWord {
        val result = minus(1)
        value = result.value
        return result
    }
}