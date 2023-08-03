package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TraceStateWrapper : IWrapper, Map<String, String> {
    companion object {

        val Default: TraceStateWrapper
        fun builder(): TraceStateBuilderWrapper
    }

    fun toBuilder(): TraceStateBuilderWrapper
}
