package io.opentelemetry.kotlindelegate.context

import io.opentelemetry.kotlindelegate.js.ROOT_CONTEXT
import io.opentelemetry.kotlindelegate.utils.coroutines.JsContextContinuationInterceptor
import io.opentelemetry.kotlindelegate.utils.java.*
import kotlinx.coroutines.withContext
import io.opentelemetry.kotlindelegate.js.Context as JsContext
import io.opentelemetry.kotlindelegate.js.ContextKey as JsContextKey
import io.opentelemetry.kotlindelegate.js.context as JsContextAPI

actual interface Context {

    actual fun <V> get(key: ContextKey<V>): V?
    actual fun <V> with(
        k1: ContextKey<V>,
        v1: V,
    ): Context

    actual fun with(value: ImplicitContextKeyed): Context {
        return value.storeInContext(this)
    }

    actual fun wrap(runnable: Runnable): Runnable {

        return Runnable {
            JsContextAPI.with(this.asJsContext(), {
                runnable.run()
            })
        }
    }

    actual fun <T, U> wrapFunction(function: Function<T, U>): Function<T, U> {
        return Function { t ->
            JsContextAPI.with(this.asJsContext(), {
                function.apply(t)
            })
        }
    }

    actual fun <T, U, V> wrapFunction(function: BiFunction<T, U, V>): BiFunction<T, U, V> {
        return BiFunction { t, u ->
            JsContextAPI.with(this.asJsContext(), {
                function.apply(t, u)
            })
        }
    }

    actual fun <T> wrapConsumer(consumer: Consumer<T>): Consumer<T> {
        return Consumer { t ->
            JsContextAPI.with(this.asJsContext(), {
                consumer.accept(t)
            })
        }
    }

    actual fun <T, U> wrapConsumer(consumer: BiConsumer<T, U>): BiConsumer<T, U> {
        return BiConsumer { t, u ->
            JsContextAPI.with(this.asJsContext(), {
                consumer.accept(t, u)
            })
        }
    }

    actual fun <T> wrapSupplier(supplier: Supplier<T>): Supplier<T> {
        return Supplier {
            JsContextAPI.with(this.asJsContext(), {
                supplier.get()
            })
        }
    }
}

actual object ContextStatic {

    actual fun current(): Context {
        return ContextCommonAdapter(JsContextAPI.active())
    }

    actual fun root(): Context {
        return RootContextCommonAdapter
    }
}

private class ContextJsAdapter(val context: Context) : JsContext {

    override fun deleteValue(key: JsContextKey): JsContext {
        return context.with(key.asCommonContextKey(), null).asJsContext()
    }

    override fun getValue(key: JsContextKey): dynamic {
        return context.get(key.asCommonContextKey())
    }

    override fun setValue(
        key: JsContextKey,
        value: dynamic,
    ): JsContext {
        return context.with(key.asCommonContextKey(), value).asJsContext()
    }
}

private open class ContextCommonAdapter(val context: JsContext) : Context {

    override fun <V> get(key: ContextKey<V>): V? {
        return context.getValue(key.jsContextKey)?.unsafeCast<V>()
    }

    override fun <V> with(k1: ContextKey<V>, v1: V): Context {
        return context.setValue(k1.jsContextKey, v1).asCommonContext()
    }
}

private object RootContextCommonAdapter : ContextCommonAdapter(ROOT_CONTEXT)

internal fun JsContext.asCommonContext(): Context = when (this) {
    is ContextJsAdapter -> this.context
    ROOT_CONTEXT -> RootContextCommonAdapter
    else -> ContextCommonAdapter(this)
}

internal fun Context.asJsContext(): JsContext = when (this) {
    is ContextCommonAdapter -> this.context
    else -> ContextJsAdapter(this)
}

fun <R> Context.runWithActiveImpl(block: () -> R): R {
    return JsContextAPI.with(this.asJsContext(), {
        block()
    })
}

suspend fun <R> Context.runWithActiveSuspendImpl(block: suspend () -> R): R {
    return withContext(JsContextContinuationInterceptor(asJsContext())) {
        block()
    }
}

actual inline fun <R> Context.runWithActive(crossinline block: () -> R): R {
    return runWithActiveImpl { block() }
}

actual suspend inline fun <R> Context.runWithActiveSuspend(crossinline block: suspend () -> R): R {
    return runWithActiveSuspendImpl { block() }
}
