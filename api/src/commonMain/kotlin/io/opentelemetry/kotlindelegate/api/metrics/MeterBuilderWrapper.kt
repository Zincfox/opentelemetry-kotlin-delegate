package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper

expect class MeterBuilderWrapper : IWrapper {

    var schemaUrl: String
    var instrumentationVersion: String
    fun build(): MeterWrapper
}
