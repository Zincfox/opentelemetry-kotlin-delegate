package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TraceStateBuilder
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase

actual class TraceStateBuilderWrapper(
    override val wrappedObject: TraceStateBuilder
) : WrapperBase<TraceStateBuilder>(), IWrapper {

    actual fun put(
        key: String,
        value: String,
    ): TraceStateBuilderWrapper {
        wrappedObject.put(key, value)
        return this
    }

    actual fun remove(key: String): TraceStateBuilderWrapper {
        wrappedObject.remove(key)
        return this
    }

    actual fun build(): TraceStateWrapper {
        return wrappedObject.build().let(::TraceStateWrapper)
    }
}
