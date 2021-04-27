package io.johnnystarr.jvm65.core

interface MMU {
    var memory: HashMap<Int, UnsignedByte>
    fun at(index: Int): Register?
}