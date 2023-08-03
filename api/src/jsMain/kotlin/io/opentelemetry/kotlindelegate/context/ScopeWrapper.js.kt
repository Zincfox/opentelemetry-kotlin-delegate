package io.opentelemetry.kotlindelegate.context

actual class ScopeWrapper : ICloseableWrapper {
    actual companion object {

        actual val Noop: ScopeWrapper
            get() = TODO("Not yet implemented")
    }
}
