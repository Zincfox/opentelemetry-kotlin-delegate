package io.opentelemetry.kotlindelegate.api

import io.opentelemetry.kotlindelegate.utils.IWrapper

interface ICloseableWrapper : IWrapper {

    fun close()
}
