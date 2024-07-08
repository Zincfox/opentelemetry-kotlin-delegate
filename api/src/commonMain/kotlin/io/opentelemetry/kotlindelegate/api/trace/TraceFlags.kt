package io.opentelemetry.kotlindelegate.api.trace


expect interface TraceFlags {
    fun asByte(): Byte
    fun asHex(): String
    fun isSampled(): Boolean
}

expect object TraceFlagsStatic {
    fun fromHex(src: CharSequence, srcOffset: Int): TraceFlags
    fun fromByte(traceFlagsByte: Byte): TraceFlags
}
