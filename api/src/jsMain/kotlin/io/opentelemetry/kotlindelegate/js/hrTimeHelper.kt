package io.opentelemetry.kotlindelegate.js

fun nanosToHrTime(nanos: Long): HrTime {
    return jsPairOf((nanos / 1_000_000_000).toDouble(), (nanos % 1_000_000_000).toDouble())
}

fun millisToHrTime(millis: Long): HrTime {
    val seconds = millis / 1000
    return jsPairOf(seconds.toDouble(), ((millis - seconds * 1000) * 1_000_000).toDouble())
}

fun HrTime.toNanos(): Long {
    return first.toLong()*1_000_000_000L + second.toLong()
}
