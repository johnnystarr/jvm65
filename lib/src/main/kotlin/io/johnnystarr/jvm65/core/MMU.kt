package io.johnnystarr.jvm65.core

interface MMU {
    var memory: HashMap<Int, UnsignedByte>
    fun at(index: Int): Register?
    fun atX(index: Int): Register?
    fun atY(index: Int): Register?
    fun put(address: Int, byte: Register)
}