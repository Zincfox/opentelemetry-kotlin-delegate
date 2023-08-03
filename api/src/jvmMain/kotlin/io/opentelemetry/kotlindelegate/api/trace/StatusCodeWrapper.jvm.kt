package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.StatusCode
import io.opentelemetry.kotlindelegate.utils.IWrapper

actual enum class StatusCodeWrapper(override val wrappedObject: StatusCode) : IWrapper {
    UNSET(StatusCode.UNSET),
    OK(StatusCode.OK),
    ERROR(StatusCode.ERROR),
    ;

    companion object {

        private val lookupMap: Map<StatusCode, StatusCodeWrapper> =
            StatusCodeWrapper.entries.associateBy { it.wrappedObject }

        fun fromOriginal(original: StatusCode): StatusCodeWrapper {
            return lookupMap.getValue(original)
        }
    }
}
