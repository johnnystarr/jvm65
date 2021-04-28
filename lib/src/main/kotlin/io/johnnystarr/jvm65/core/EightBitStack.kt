package io.johnnystarr.jvm65.core

/**
 * Concrete class for 6502 Stack
 * @constructor creates an EightBitStack
 */
class EightBitStack() : Stack<UnsignedByte> {

    override val elements: MutableList<UnsignedByte> = mutableListOf()

    /**
     * Pushes a byte to the stack
     * @param element the byte pushed on the stack
     * @return Unit
     */
    override fun push(element: UnsignedByte) {
        this.elements.add(element)
    }

    override fun peek() : UnsignedByte {
        return this.elements.last()
    }

    override fun pop() : UnsignedByte {
        val element = this.elements[this.elements.size -1]
        this.elements.removeAt(this.elements.size - 1)
        return element
    }

    override fun isEmpty(): Boolean {
        return this.elements.isEmpty()
    }

    override fun size() : Int {
        return this.elements.size
    }
}