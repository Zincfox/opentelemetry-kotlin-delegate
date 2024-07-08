package io.opentelemetry.kotlindelegate.utils.java

expect fun interface Runnable {

    fun run()
}

expect fun interface Consumer<T> {

    fun accept(t: T)
}

expect fun interface BiConsumer<T, U> {

    fun accept(t: T, u: U)
}

expect fun interface Predicate<T> {
    fun test(t: T): Boolean
}

expect fun interface Function<T, U> {
    fun apply(t: T): U
}

expect fun interface BiFunction<T, U, V> {
    fun apply(t: T, u: U): V
}

expect fun interface Supplier<T> {
    fun get(): T
}
