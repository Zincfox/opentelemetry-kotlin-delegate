package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.SpanKind as JsSpanKind

actual enum class SpanKind(internal val jsKind: JsSpanKind) {
    INTERNAL(JsSpanKind.INTERNAL),
    SERVER(JsSpanKind.SERVER),
    CLIENT(JsSpanKind.CLIENT),
    PRODUCER(JsSpanKind.PRODUCER),
    CONSUMER(JsSpanKind.CONSUMER),
    ;
}


fun JsSpanKind.asCommonSpanKind(): SpanKind = when (this) {
    JsSpanKind.INTERNAL -> SpanKind.INTERNAL
    JsSpanKind.SERVER -> SpanKind.SERVER
    JsSpanKind.CLIENT -> SpanKind.CLIENT
    JsSpanKind.PRODUCER -> SpanKind.PRODUCER
    JsSpanKind.CONSUMER -> SpanKind.CONSUMER
}

fun SpanKind.asJsSpanKind(): JsSpanKind = this.jsKind
