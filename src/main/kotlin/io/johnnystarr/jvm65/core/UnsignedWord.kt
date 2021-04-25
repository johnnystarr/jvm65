package io.johnnystarr.jvm65.core

data class UnsignedWord(var value: Int) : Unsigned {

    override operator fun plus(increment: Int): UnsignedWord {
        val newValue = value + increment
        return when {
            newValue == 65536 -> UnsignedWord(0)
            newValue > 65536  -> UnsignedWord(newValue - 65536)
            else -> UnsignedWord(newValue)
        }
    }

    override operator fun minus(decrement: Int): UnsignedWord {
        val newValue = value - decrement
        return when {
            newValue == -1 -> UnsignedWord(0xFFFF)
            newValue < -1 -> UnsignedWord(newValue + 65536)
            else -> UnsignedWord(newValue)
        }
    }

    override fun shiftLeft() {
        this.value = this.value shl 1
    }

    override fun shiftRight() {
        this.value = this.value ushr 1
    }
}