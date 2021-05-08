/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Concrete Class for 6502 Stack
 *
 * @constructor creates an EightBitStack
 */
class EightBitStack(var cpu: P6502) : Stack<UnsignedByte> {

    override val elements: MutableList<UnsignedByte> = mutableListOf()

    /**
     * Pushes a byte to the stack
     * @param element [UnsignedByte] the byte pushed on the stack
     * @return [Unit]
     */
    override fun push(element: UnsignedByte) {
        this.elements.add(element)
    }

    /**
     * Peeks the stack for the last element
     * @return [UnsignedByte] returns last byte
     */
    override fun peek() : UnsignedByte {
        return this.elements.last()
    }

    /**
     * Pops an element off the stack
     * @return [UnsignedByte] returns the byte on the top
     */
    override fun pop() : UnsignedByte {
        val element = this.elements[this.elements.size -1]
        this.elements.removeAt(this.elements.size - 1)
        return element
    }

    /**
     * Predicate to determine if stack is empty
     * @return [Boolean] true or false
     */
    override fun isEmpty(): Boolean {
        return this.elements.isEmpty()
    }

    /**
     * Provides the number of elements in the stack
     * @return [Int] size of stack
     */
    override fun size() : Int {
        return this.elements.size
    }
}