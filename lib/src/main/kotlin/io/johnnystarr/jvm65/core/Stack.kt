/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Generic Stack Interface
 * @property T the class that implements the stack
 */
interface Stack<T> {
    val elements: MutableList<T>
    fun push(element: T)
    fun peek() : T
    fun pop() : T
    fun isEmpty(): Boolean
    fun size() : Int
}