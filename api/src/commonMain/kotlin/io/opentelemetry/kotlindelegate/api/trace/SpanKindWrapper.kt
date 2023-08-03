package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect enum class SpanKindWrapper : IWrapper {
    INTERNAL,
    SERVER,
    CLIENT,
    PRODUCER,
    CONSUMER,
    ;
}
