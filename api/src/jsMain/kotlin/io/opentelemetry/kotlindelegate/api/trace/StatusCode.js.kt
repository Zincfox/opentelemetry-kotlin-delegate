package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.js.SpanStatusCode as JsStatusCode

actual enum class StatusCode(internal val jsStatusCode: JsStatusCode) {
    UNSET(JsStatusCode.UNSET),
    OK(JsStatusCode.OK),
    ERROR(JsStatusCode.ERROR),
    ;
}

fun StatusCode.asJsStatusCode(): JsStatusCode = jsStatusCode
fun JsStatusCode.asCommonStatusCode(): StatusCode = when (this) {
    JsStatusCode.UNSET -> StatusCode.UNSET
    JsStatusCode.OK -> StatusCode.OK
    JsStatusCode.ERROR -> StatusCode.ERROR
}
