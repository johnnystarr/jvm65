package io.johnnystarr.jvm65.core

open class UnsignedRegister (open var value: Int, open var state: RegisterState = RegisterState.NONE) : Register {

    /**
     * Shift register left by 1
     * @return [Unit]
     */
    override fun shiftLeft() {
        this.value = this.value shl 1
        if (this.value == 0) this.state = RegisterState.ZEROED
    }

    /**
     * Shift register right by 1
     * @return [Unit]
     */
    override fun shiftRight() {
        this.value = this.value ushr 1
        if (this.value == 0) this.state = RegisterState.ZEROED
    }

    /**
     * Set this register value to 0
     * @return [Unit]
     */
    override fun clear() {
        this.value = 0
        this.state = RegisterState.ZEROED
    }

    /**
     * Increment register by 1
     * @return [Unit]
     */
    override fun inc() {
        this.value += 1
    }

    /**
     * Decrement register by 1
     * @return [Unit]
     */
    override fun dec() {
        this.value -= 1
    }

    /**
     * Logical AND on register
     * @return [UnsignedRegister] result of AND operation
     */
    override fun and(register: UnsignedRegister): UnsignedRegister {
        return (UnsignedRegister(this.value and register.value))
    }

    /**
     * Logical OR on register
     * @return [UnsignedRegister] result of OR operation
     */
    override fun or(register: UnsignedRegister): UnsignedRegister {
        return (UnsignedRegister(this.value or register.value))
    }

    /**
     * Logical XOR on register
     * @return [UnsignedRegister] result of XOR operation
     */
    override fun xor(register: UnsignedRegister): UnsignedRegister {
        return (UnsignedRegister(this.value xor register.value))
    }
}