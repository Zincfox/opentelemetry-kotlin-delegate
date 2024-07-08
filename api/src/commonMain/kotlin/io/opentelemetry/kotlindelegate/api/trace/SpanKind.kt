package io.opentelemetry.kotlindelegate.api.trace

expect enum class SpanKind {
    INTERNAL,
    SERVER,
    CLIENT,
    PRODUCER,
    CONSUMER,
    ;
}
