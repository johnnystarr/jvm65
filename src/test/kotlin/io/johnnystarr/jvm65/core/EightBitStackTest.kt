package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EightBitStackTest {
    private lateinit var stack: Stack<UnsignedByte>

    @BeforeEach
    fun setUp() {
        stack = EightBitStack()
    }

    @Test
    fun `push 1 element to the stack`() {
        stack.push(UnsignedByte(1))
        assertEquals(1, stack.size())
    }

    @Test
    fun `pop 1 element and check size`() {
        stack.push(UnsignedByte(1))
        stack.pop()
        assertEquals(0, stack.size())
    }

    @Test
    fun `push 2 elements on the stack and peek`() {
        stack.push(UnsignedByte(1))
        stack.push(UnsignedByte(2))
        assertEquals(2, stack.peek().value)
    }

    @Test
    fun `push 2 elements pop them and validate isEmpty`() {
        stack.push(UnsignedByte(1))
        stack.push(UnsignedByte(2))
        stack.pop()
        stack.pop()
        assertTrue(stack.isEmpty())
    }

    @Test
    fun `push then pop and evalute element`() {
        stack.push(UnsignedByte(3))
        val element = stack.pop()
        assertEquals(3, element.value)
    }
}