package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.api.metrics.MeterBuilder
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.cachedWriteOnly
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class MeterBuilderWrapper(
    override val wrappedObject: MeterBuilder,
) : WrapperBase<MeterBuilder>(), IWrapper {

    actual var schemaUrl: String by cachedWriteOnly("", wrappedObject::setSchemaUrl)
    actual var instrumentationVersion: String by cachedWriteOnly("",wrappedObject::setSchemaUrl)

    actual fun build(): MeterWrapper {
        return wrappedObject.build().letWrapImmutable(::MeterWrapper)
    }
}
