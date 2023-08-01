package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect enum class StatusCodeWrapper : IWrapper {
    UNSET,
    OK,
    ERROR,
    ;
}
