/**
 * @author Johnny Starr <johnnystarr@gmail.com>
 */

package io.johnnystarr.jvm65.core

/**
 * Register States
 */
enum class RegisterState {
    NONE,
    ZEROED,
    POSITIVE_WRAPAROUND,
    NEGATIVE_WRAPAROUND,
    SIGNED_POSITIVE,
    SIGNED_NEGATIVE
}