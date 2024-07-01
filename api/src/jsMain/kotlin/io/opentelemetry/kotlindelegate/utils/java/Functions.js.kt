package io.opentelemetry.kotlindelegate.utils.java

actual fun interface Runnable {

    actual fun run()
}

actual fun interface Consumer<T> {

    actual fun accept(t: T)
}

actual fun interface BiConsumer<T, U> {

    actual fun accept(t: T, u: U)
}

actual fun interface Predicate<T> {

    actual fun test(t: T): Boolean
}

actual fun interface Function<T, U> {

    actual fun apply(t: T): U
}

actual fun interface BiFunction<T, U, V> {

    actual fun apply(t: T, u: U): V
}

actual fun interface Supplier<T> {

    actual fun get(): T
}
