package io.johnnystarr.jvm65.core

interface Processor {
    fun execute(): Boolean
    fun status(): UnsignedByte
}