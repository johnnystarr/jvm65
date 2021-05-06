/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Base class for Unsigned objects
 * @param value [Int] default value of register
 * @param state [RegisterState] default state of register
 * @constructor creates an Unsigned Register
 */
open class UnsignedRegister (
    open var value: Int,
    open var state: RegisterState = RegisterState.NONE
) : Register {

    /**
     * Shift register left by 1
     * @return [Unit]
     */
    override fun shiftLeft() {
        value = value shl 1
        if (value == 0) state = RegisterState.ZEROED
    }

    /**
     * Shift register right by 1
     * @return [Unit]
     */
    override fun shiftRight() {
        value = value ushr 1
        if (value == 0) state = RegisterState.ZEROED
    }

    /**
     * Set register value to 0
     * @return [Unit]
     */
    override fun clear() {
        value = 0
        state = RegisterState.ZEROED
    }

    /**
     * Logical AND on register
     * @return [UnsignedRegister] result of AND operation
     */
    override fun and(register: UnsignedRegister): UnsignedByte {
        return (UnsignedByte(value and register.value))
    }

    /**
     * Logical OR on register
     * @return [UnsignedRegister] result of OR operation
     */
    override fun or(register: UnsignedRegister): UnsignedByte {
        return (UnsignedByte(value or register.value))
    }

    /**
     * Logical XOR on register
     * @return [UnsignedRegister] result of XOR operation
     */
    override fun xor(register: UnsignedRegister): UnsignedByte {
        return (UnsignedByte(value xor register.value))
    }
}