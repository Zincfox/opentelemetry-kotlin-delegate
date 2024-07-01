package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.SpanKind as JsSpanKind

actual enum class SpanKind(val jsKind: JsSpanKind) {
    INTERNAL(JsSpanKind.INTERNAL),
    SERVER(JsSpanKind.SERVER),
    CLIENT(JsSpanKind.CLIENT),
    PRODUCER(JsSpanKind.PRODUCER),
    CONSUMER(JsSpanKind.CONSUMER),
    ;
}
