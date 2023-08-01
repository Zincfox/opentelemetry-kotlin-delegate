package io.opentelemetry.kotlindelegate.api.trace

actual class TraceFlagsWrapper : IWrapper {
    actual companion object {

        actual val Length: Int
            get() = TODO("Not yet implemented")

        actual val Default: TraceFlagsWrapper
            get() = TODO("Not yet implemented")

        actual val Sampled: TraceFlagsWrapper
            get() = TODO("Not yet implemented")


        actual fun fromHex(
            src: CharSequence,
            srcOffset: Int,
        ): TraceFlagsWrapper {
            TODO("Not yet implemented")
        }

        actual fun fromByte(traceFlagsByte: Byte): TraceFlagsWrapper {
            TODO("Not yet implemented")
        }
    }

    actual val isSampled: Boolean
        get() = TODO("Not yet implemented")

    actual fun asHex(): String {
        TODO("Not yet implemented")
    }

    actual fun asByte(): Byte {
        TODO("Not yet implemented")
    }
}
