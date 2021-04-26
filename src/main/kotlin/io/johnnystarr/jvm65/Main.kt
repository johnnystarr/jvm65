package io.johnnystarr.jvm65

import io.johnnystarr.jvm65.core.MemoryManager

fun main(args: Array<String>) {
    val map = MemoryManager(0xFFFF)
    map.memory[0x0A]?.value = 13

    println(map.memory[0x0A])
}