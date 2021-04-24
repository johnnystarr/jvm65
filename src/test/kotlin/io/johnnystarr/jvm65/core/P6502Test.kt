package io.johnnystarr.jvm65.core

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class P6502Test {
    private lateinit var cpu: Processor

    @BeforeEach
    fun setUp() {
        cpu = P6502()
    }
}