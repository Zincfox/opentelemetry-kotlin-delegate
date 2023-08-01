package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TraceStateBuilderWrapper : IWrapper {

    fun put(key: String, value: String): TraceStateBuilderWrapper
    fun remove(key: String): TraceStateBuilderWrapper
    fun build(): TraceStateWrapper
}
