/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * CPU Instruction Set
 */
interface InstructionSet {
    fun adc(mode: AddressMode) // Add with carry
    fun and(mode: AddressMode) // And
    fun asl(mode: AddressMode) // Arithmetic shift left
    fun bcc(mode: AddressMode) // Branch on carry clear
    fun bcs(mode: AddressMode) // Branch on carry set
    fun beq(mode: AddressMode) // Branch on equal
    fun bit(mode: AddressMode) // Bit test
    fun bmi(mode: AddressMode) // Branch on minus
    fun bne(mode: AddressMode) // Branch on not equal
    fun bpl(mode: AddressMode) // Branch on plus
    fun brk(mode: AddressMode) // Break / interrupt
    fun bvc(mode: AddressMode) // Branch on overflow clear
    fun bvs(mode: AddressMode) // Branch on overflow set
    fun clc(mode: AddressMode) // Clear carry
    fun cld(mode: AddressMode) // Clear decimal
    fun cli(mode: AddressMode) // Clear interrupt disable
    fun clv(mode: AddressMode) // Clear overflow
    fun cmp(mode: AddressMode) // Compare with accumulator
    fun cpx(mode: AddressMode) // Compare with X
    fun cpy(mode: AddressMode) // Compare with Y
    fun dec(mode: AddressMode) // Decrement
    fun dex(mode: AddressMode) // Decrement X
    fun dey(mode: AddressMode) // Decrement Y
    fun eor(mode: AddressMode) // Exclusive or
    fun inc(mode: AddressMode) // Increment
    fun inx(mode: AddressMode) // Increment X
    fun iny(mode: AddressMode) // Increment Y
    fun jmp(mode: AddressMode) // Jump
    fun jsr(mode: AddressMode) // Jump subroutine
    fun lda(mode: AddressMode) // Load accumulator
    fun ldx(mode: AddressMode) // Load X
    fun ldy(mode: AddressMode) // Load Y
    fun lsr(mode: AddressMode) // Logical shift right
    fun nop(mode: AddressMode) // No operation
    fun ora(mode: AddressMode) // Or with accumulator
    fun pha(mode: AddressMode) // Push accumulator onto stack
    fun php(mode: AddressMode) // Push processor status onto stack
    fun pla(mode: AddressMode) // Pull accumulator from stack
    fun plp(mode: AddressMode) // Pull processor status from stack
    fun rol(mode: AddressMode) // Rotate left
    fun ror(mode: AddressMode) // Rotate right
    fun rti(mode: AddressMode) // Return from interrupt
    fun rts(mode: AddressMode) // Return from subroutine
    fun sbc(mode: AddressMode) // Subtract with carry
    fun sec(mode: AddressMode) // Set carry
    fun sed(mode: AddressMode) // Set decimal
    fun sei(mode: AddressMode) // Set interrupt disable
    fun sta(mode: AddressMode) // Store accumulator
    fun stx(mode: AddressMode) // Store X
    fun sty(mode: AddressMode) // Store Y
    fun tax(mode: AddressMode) // Transfer accumulator to X
    fun tay(mode: AddressMode) // Transfer accumulator to Y
    fun tsx(mode: AddressMode) // Transfer stack pointer to X
    fun txa(mode: AddressMode) // Transfer X to accumulator
    fun txs(mode: AddressMode) // Transfer X to stack pointer
    fun tya(mode: AddressMode) // Transfer Y to accumulator
}