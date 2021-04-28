/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Processor Interface to allow extensions
 */
interface Processor {
    fun execute(): Boolean
    fun status(): Register
    fun step(): Register?
    fun peek(): Register?
}