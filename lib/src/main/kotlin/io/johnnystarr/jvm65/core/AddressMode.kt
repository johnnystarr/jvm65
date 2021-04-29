/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Address Modes
 */
enum class AddressMode {
    ACCUMULATOR, ABSOLUTE, ABSOLUTE_X, ABSOLUTE_Y, IMPLIED, INDIRECT,
    INDIRECT_X, INDIRECT_Y, RELATIVE, ZEROPAGE, ZEROPAGE_X, ZEROPAGE_Y,
    IMMEDIATE
}