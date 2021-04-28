package io.johnnystarr.jvm65.core

interface Processor {
    fun execute(): Boolean
    fun status(): Register
    fun step(): Register?
    fun peek(): Register?
}