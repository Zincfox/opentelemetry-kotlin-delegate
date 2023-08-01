package io.opentelemetry.kotlindelegate.api.trace

import io.opentelemetry.api.trace.TracerBuilder
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly

actual class TracerBuilderWrapper(
    override val wrappedObject: TracerBuilder
) : WrapperBase<TracerBuilder>(), IWrapper {

    actual var schemaUrl: String by cachedWriteOnly("", wrappedObject::setSchemaUrl)
    actual var instrumentationVersion: String by cachedWriteOnly("",wrappedObject::setInstrumentationVersion)

    actual fun build(): TracerWrapper {
        return wrappedObject.build().let(::TracerWrapper)
    }
}
