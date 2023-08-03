package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class TracerBuilderWrapper : IWrapper {

    var schemaUrl: String
    var instrumentationVersion: String
    fun build(): TracerWrapper
}
