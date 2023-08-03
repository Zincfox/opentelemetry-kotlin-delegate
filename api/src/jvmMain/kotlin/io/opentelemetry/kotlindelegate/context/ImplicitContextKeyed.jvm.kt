package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.context.ImplicitContextKeyed
import io.opentelemetry.kotlindelegate.utils.IWrapper
import io.opentelemetry.kotlindelegate.utils.WrapperBase
import io.opentelemetry.kotlindelegate.utils.letWrapImmutable

actual open class ImplicitContextKeyedWrapper(
    override val wrappedObject: ImplicitContextKeyed
) : WrapperBase<ImplicitContextKeyed>(), IWrapper {

    actual open fun makeCurrent() {
        wrappedObject.makeCurrent()
    }

    actual open fun storeInContext(context: ContextWrapper): ContextWrapper {
        return wrappedObject.storeInContext(context.wrappedObject).letWrapImmutable(::ContextWrapper)
    }
}
