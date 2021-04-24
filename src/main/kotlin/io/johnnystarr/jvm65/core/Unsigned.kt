package io.johnnystarr.jvm65.core

interface Unsigned {
    operator fun plus(increment: Int): Unsigned
    operator fun minus(decrement: Int): Unsigned
}