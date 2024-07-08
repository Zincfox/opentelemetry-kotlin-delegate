package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asCommonContext
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.propagation as JsPropagationAPI

actual interface ContextPropagators {

    actual fun getTextMapPropagator(): TextMapPropagator
}

internal object DelegateTextmapPropagator : TextMapPropagator {

    override fun fields(): Collection<String> {
        return JsPropagationAPI.fields().toList()
    }

    override fun <C : Any> extract(context: Context, carrier: C?, getter: TextMapGetter<C>): Context {
        return if (carrier == null) {
            JsPropagationAPI.extract(context.asJsContext(), Unit, getter.asNullUnitJsGetter()).asCommonContext()
        } else {
            JsPropagationAPI.extract(context.asJsContext(), carrier, getter.asJsGetter()).asCommonContext()
        }
    }

    override fun <C : Any> inject(context: Context, carrier: C?, setter: TextMapSetter<C>) {
        if (carrier == null) {
            JsPropagationAPI.inject(context.asJsContext(), Unit, setter.asNullUnitJsSetter())
        } else {
            JsPropagationAPI.inject(context.asJsContext(), carrier, setter.asJsSetter())
        }
    }
}

internal object DelegateContextPropagator : ContextPropagators {

    override fun getTextMapPropagator(): TextMapPropagator {
        return DelegateTextmapPropagator
    }
}

actual object ContextPropagatorsStatic {

    private val NoopContextPropagators: ContextPropagators = object : ContextPropagators {
        override fun getTextMapPropagator(): TextMapPropagator {
            return TextMapPropagatorStatic.noop()
        }
    }

    actual fun noop(): ContextPropagators = NoopContextPropagators

    actual fun create(textPropagator: TextMapPropagator): ContextPropagators {
        return object : ContextPropagators {
            override fun getTextMapPropagator(): TextMapPropagator {
                return textPropagator
            }
        }
    }
}
