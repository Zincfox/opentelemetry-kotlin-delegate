package io.opentelemetry.kotlindelegate.js

external interface JsPair<A, B>

var <A> JsPair<A, *>.first: A
    get() = (this.unsafeCast<Array<A>>()[0])
    set(value) {
        this.unsafeCast<Array<A>>()[0] = value
    }

var <B> JsPair<*, B>.second: B
    get() = (this.unsafeCast<Array<B>>()[0])
    set(value) {
        this.unsafeCast<Array<B>>()[0] = value
    }

fun <A, B> jsPairOf(first: A, second: B): JsPair<A, B> = arrayOf<dynamic>(first, second).unsafeCast<JsPair<A, B>>()
