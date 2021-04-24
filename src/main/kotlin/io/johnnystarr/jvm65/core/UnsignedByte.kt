package io.johnnystarr.jvm65.core

data class UnsignedByte(var value: Int) {
    operator fun plus(increment: Int): UnsignedByte {
        val newValue = value + increment
        return when {
            newValue == 256 -> UnsignedByte(0)
            newValue > 256  -> UnsignedByte(newValue - 256)
            else -> UnsignedByte(newValue)
        }
    }
}