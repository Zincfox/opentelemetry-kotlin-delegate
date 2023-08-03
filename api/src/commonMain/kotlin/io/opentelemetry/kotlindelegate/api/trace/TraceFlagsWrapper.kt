package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TraceFlagsWrapper : IWrapper {
    companion object {

        val Length: Int
        val Default: TraceFlagsWrapper
        val Sampled: TraceFlagsWrapper
        fun fromHex(src: CharSequence, srcOffset: Int): TraceFlagsWrapper
        fun fromByte(traceFlagsByte: Byte): TraceFlagsWrapper
    }

    val isSampled: Boolean
    fun asHex(): String
    fun asByte(): Byte
}
