package io.opentelemetry.kotlindelegate.context.propagation

typealias TextMapSetterWrapper<C> = (carrier: C?, key:String, value: String) -> Unit
