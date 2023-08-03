package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.context.Scope
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.api.ICloseableWrapper
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual class ScopeWrapper(
    override val wrappedObject: Scope
) : WrapperBase<Scope>(), ICloseableWrapper {
    actual companion object {

        actual val Noop: ScopeWrapper by lazy {
            Scope.noop().letWrapImmutable(::ScopeWrapper)
        }
    }

    override fun close() {
        wrappedObject.close()
    }
}
