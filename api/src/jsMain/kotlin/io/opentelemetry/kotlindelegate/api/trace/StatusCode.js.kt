package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.SpanStatusCode as JsStatusCode

actual enum class StatusCode(val jsStatusCode: JsStatusCode) {
    UNSET(JsStatusCode.UNSET),
    OK(JsStatusCode.OK),
    ERROR(JsStatusCode.ERROR),
    ;
}
