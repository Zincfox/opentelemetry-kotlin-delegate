package io.opentelemetry.kotlindelegate.api.trace

actual enum class SpanKindWrapper : IWrapper { INTERNAL,
    SERVER,
    CLIENT,
    PRODUCER,
    CONSUMER,
    ;
}
