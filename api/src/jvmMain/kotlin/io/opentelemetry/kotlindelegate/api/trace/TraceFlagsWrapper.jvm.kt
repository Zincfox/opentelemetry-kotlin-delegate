package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TraceFlags
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class TraceFlagsWrapper(override val wrappedObject: TraceFlags) : WrapperBase<TraceFlags>(), IWrapper {
    actual companion object {

        actual val Length: Int = TraceFlags.getLength()

        actual val Default: TraceFlagsWrapper by lazy {
            TraceFlags.getDefault().letWrapImmutable(::TraceFlagsWrapper)
        }

        actual val Sampled: TraceFlagsWrapper by lazy {
            TraceFlags.getSampled().letWrapImmutable(::TraceFlagsWrapper)
        }

        actual fun fromHex(
            src: CharSequence,
            srcOffset: Int,
        ): TraceFlagsWrapper {
            return TraceFlags.fromHex(src, srcOffset).letWrapImmutable(::TraceFlagsWrapper)
        }

        actual fun fromByte(traceFlagsByte: Byte): TraceFlagsWrapper {
            return TraceFlags.fromByte(traceFlagsByte).letWrapImmutable(::TraceFlagsWrapper)
        }
    }

    actual val isSampled: Boolean = wrappedObject.isSampled

    actual fun asHex(): String {
        return wrappedObject.asHex()
    }

    actual fun asByte(): Byte {
        return wrappedObject.asByte()
    }
}
