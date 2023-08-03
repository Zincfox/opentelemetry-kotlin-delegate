package io.opentelemetry.kotlindelegate.utils

abstract class WrapperBase<T : Any> : IWrapper {

    abstract override val wrappedObject: T

    override fun hashCode(): Int {
        return wrappedObject.hashCode()
    }

    override fun toString(): String {
        return "Wrapper($wrappedObject)"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is WrapperBase<*>) return false
        return wrappedObject == other.wrappedObject
    }
}
