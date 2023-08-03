package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.api.ICloseableWrapper

expect class ScopeWrapper : ICloseableWrapper {
    companion object {

        val Noop: ScopeWrapper
    }
}
