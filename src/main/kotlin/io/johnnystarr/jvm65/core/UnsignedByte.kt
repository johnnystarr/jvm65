package io.johnnystarr.jvm65.core

data class UnsignedByte(var value: Int) : Unsigned {
    override operator fun plus(increment: Int): UnsignedByte {
        val newValue = value + increment
        return when {
            newValue == 256 -> UnsignedByte(0)
            newValue > 256  -> UnsignedByte(newValue - 256)
            else -> UnsignedByte(newValue)
        }
    }

    override operator fun minus(decrement: Int): UnsignedByte {
        val newValue = value - decrement
        return when {
            newValue == -1 -> UnsignedByte(0xFF)
            newValue < -1 -> UnsignedByte(newValue + 256)
            else -> UnsignedByte(newValue)
        }
    }
}