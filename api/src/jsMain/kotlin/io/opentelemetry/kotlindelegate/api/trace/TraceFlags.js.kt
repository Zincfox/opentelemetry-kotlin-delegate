package io.opentelemetry.kotlindelegate.api.trace

import kotlin.experimental.and
import kotlin.math.min

actual interface TraceFlags {

    actual fun asByte(): Byte
    actual fun asHex(): String
    actual fun isSampled(): Boolean
}

internal class UnknownTraceFlagImpl(val byte: Byte) : TraceFlags {

    override fun asHex(): String {
        return byte.toString(16).padStart(2, '0')
    }

    override fun isSampled(): Boolean {
        return (byte.toInt() and 0x01) == 0x01
    }

    override fun asByte(): Byte {
        return byte
    }
}

actual object TraceFlagsStatic {

    object KnownUnsampledTraceFlags : TraceFlags {

        override fun asByte(): Byte {
            return 0x00
        }

        override fun asHex(): String {
            return "00"
        }

        override fun isSampled(): Boolean {
            return false
        }
    }

    object KnownSampledTraceFlags : TraceFlags {

        override fun asByte(): Byte {
            return 0x01
        }

        override fun asHex(): String {
            return "01"
        }

        override fun isSampled(): Boolean {
            return true
        }
    }


    actual fun fromHex(
        src: CharSequence,
        srcOffset: Int,
    ): TraceFlags = fromByte(src.substring(srcOffset, min(src.length, srcOffset + 2)).toByte(16))

    actual fun fromByte(traceFlagsByte: Byte): TraceFlags = when (traceFlagsByte) {
        0x00.toByte() -> KnownUnsampledTraceFlags
        0x01.toByte() -> KnownSampledTraceFlags
        else -> UnknownTraceFlagImpl(traceFlagsByte)
    }
}
