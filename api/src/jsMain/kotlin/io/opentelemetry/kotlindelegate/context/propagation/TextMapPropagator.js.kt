package io.opentelemetry.kotlindelegate.context.propagation

import io.opentelemetry.kotlindelegate.context.Context
import io.opentelemetry.kotlindelegate.context.asCommonContext
import io.opentelemetry.kotlindelegate.context.asJsContext
import io.opentelemetry.kotlindelegate.js.Context as JsContext
import io.opentelemetry.kotlindelegate.js.TextMapGetter as JsTextMapGetter
import io.opentelemetry.kotlindelegate.js.TextMapPropagator as JsTextMapPropagator
import io.opentelemetry.kotlindelegate.js.TextMapSetter as JsTextMapSetter

actual interface TextMapPropagator {

    actual fun fields(): Collection<String>
    actual fun <C> inject(
        context: Context,
        carrier: C?,
        setter: TextMapSetter<C>,
    )

    actual fun <C> extract(
        context: Context,
        carrier: C?,
        getter: TextMapGetter<C>,
    ): Context
}

actual object TextMapPropagatorStatic {

    private object Noop : TextMapPropagator {

        override fun fields(): Collection<String> = emptyList()
        override fun <C> inject(context: Context, carrier: C?, setter: TextMapSetter<C>) {}
        override fun <C> extract(context: Context, carrier: C?, getter: TextMapGetter<C>): Context = context
    }

    actual fun composite(vararg propagators: TextMapPropagator): TextMapPropagator {
        return CompositeTextMapPropagator(propagators.toList())
    }

    actual fun composite(propagators: Iterable<TextMapPropagator>): TextMapPropagator {
        return CompositeTextMapPropagator(propagators)
    }

    actual fun noop(): TextMapPropagator = Noop
}

internal class CompositeTextMapPropagator(children: Iterable<TextMapPropagator>) : TextMapPropagator {

    private val children: List<TextMapPropagator> = children.flatMap {
        if (it is CompositeTextMapPropagator) it.children
        else listOf(it)
    }

    override fun fields(): Collection<String> {
        return children.flatMapTo(mutableSetOf()) { it.fields() }
    }

    override fun <C> extract(context: Context, carrier: C?, getter: TextMapGetter<C>): Context {
        return children.fold(context) { ctx, prop ->
            prop.extract(ctx, carrier, getter)
        }
    }

    override fun <C> inject(context: Context, carrier: C?, setter: TextMapSetter<C>) {
        children.forEach { it.inject(context, carrier, setter) }
    }
}

internal class TextMapPropagatorJsAdapter<C : Any>(val propagator: TextMapPropagator) : JsTextMapPropagator<C> {

    override fun fields(): Array<String> {
        return propagator.fields().toTypedArray()
    }

    override fun inject(
        context: JsContext,
        carrier: C,
        setter: JsTextMapSetter<C>?,
    ) {
        val effectiveSetter: TextMapSetter<in C> =
            if (setter == null || setter == undefined) {
                TextMapRecordSetter.forType()
            } else {
                setter.asCommonSetter()
            }
        propagator.inject(context.asCommonContext(), carrier, effectiveSetter)
    }

    override fun extract(
        context: JsContext,
        carrier: C,
        getter: JsTextMapGetter<C>?,
    ): JsContext {
        val effectiveGetter: TextMapGetter<in C> =
            if (getter == null || getter == undefined) {
                TextMapRecordGetter.forType()
            } else {
                getter.asCommonGetter()
            }
        return propagator.extract(context.asCommonContext(), carrier, effectiveGetter).asJsContext()
    }
}
