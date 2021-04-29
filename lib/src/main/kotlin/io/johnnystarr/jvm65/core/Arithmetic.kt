package io.johnnystarr.jvm65.core

interface Arithmetic {
    operator fun plus(increment: Int): UnsignedRegister
    operator fun minus(decrement: Int): UnsignedRegister
}