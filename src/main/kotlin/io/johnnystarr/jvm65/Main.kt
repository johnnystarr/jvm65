package io.johnnystarr.jvm65

import io.johnnystarr.jvm65.core.UnsignedByte

fun main(args: Array<String>) {
    println("JVM65!")

    var byte = UnsignedByte(1)
    byte += 256

    println("The byte value is $byte")

}