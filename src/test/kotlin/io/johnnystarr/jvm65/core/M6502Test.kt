package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class M6502Test {
    private lateinit var cpu: Processor

    @BeforeEach
    fun setUp() {
        cpu = M6502()
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun execute() {
        Assertions.assertTrue(cpu.execute())
    }
}