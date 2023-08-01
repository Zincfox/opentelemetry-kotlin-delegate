package io.opentelemetry.kotlindelegate.api.metrics

import io.opentelemetry.kotlindelegate.utils.IWrapper
import kotlin.native.concurrent.ThreadLocal

internal class ObservableCallbackWrapperStore<T, W : IWrapper>(
    private val wrapperFactory: (T) -> W,
    private val callback: (W) -> Unit,
) {


    @ThreadLocal
    private var wrapper: W? = null

    fun acceptCallback(measurement: T) {
        val stored = wrapper
        if (stored != null && stored.wrappedObject == measurement) {
            callback(stored)
            return
        } else {
            val newWrapper = wrapperFactory(measurement)
            callback(newWrapper)
            wrapper = newWrapper
        }
    }
}
