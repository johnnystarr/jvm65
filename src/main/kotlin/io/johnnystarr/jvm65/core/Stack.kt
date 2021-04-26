package io.johnnystarr.jvm65.core

interface Stack<T> {
    val elements: MutableList<T>
    fun push(element: T)
    fun peek() : T
    fun pop()
    fun isEmpty(): Boolean
    fun size() : Int
}