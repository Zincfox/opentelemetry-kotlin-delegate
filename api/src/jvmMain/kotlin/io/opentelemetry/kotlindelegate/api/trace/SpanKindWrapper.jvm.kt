package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.kotlindelegate.utils.IWrapper

actual enum class SpanKindWrapper(override val wrappedObject: SpanKind) : IWrapper {
    INTERNAL(SpanKind.INTERNAL),
    SERVER(SpanKind.SERVER),
    CLIENT(SpanKind.CLIENT),
    PRODUCER(SpanKind.PRODUCER),
    CONSUMER(SpanKind.CONSUMER),
    ;

    companion object {

        private val lookupMap: Map<SpanKind, SpanKindWrapper> =
            SpanKindWrapper.entries.associateBy { it.wrappedObject }

        fun fromOriginal(original: SpanKind): SpanKindWrapper {
            return lookupMap.getValue(original)
        }
    }
}
