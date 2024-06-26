package io.opentelemetry.kotlindelegate.api.trace

actual typealias TraceFlags = io.opentelemetry.api.trace.TraceFlags

actual object TraceFlagsStatic {

    actual fun fromHex(
        src: CharSequence,
        srcOffset: Int,
    ): TraceFlags = TraceFlags.fromHex(src, srcOffset)

    actual fun fromByte(traceFlagsByte: Byte): TraceFlags = TraceFlags.fromByte(traceFlagsByte)
}
