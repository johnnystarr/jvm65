package io.johnnystarr.jvm65.core

data class UnsignedByte(var value: Int) {
    operator fun plus(increment: Int): UnsignedByte {
        val newValue = value + increment
        if (newValue == 256) {
            return UnsignedByte(0)
        } else if (newValue > 256) {
            return UnsignedByte(newValue - 256)
        }
        return UnsignedByte(newValue)
    }
}