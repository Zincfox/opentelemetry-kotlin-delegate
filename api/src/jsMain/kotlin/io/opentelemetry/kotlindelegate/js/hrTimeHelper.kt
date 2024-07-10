package io.opentelemetry.kotlindelegate.js

fun nanosToHrTime(nanos: Long): HrTime {
    return jsPairOf(nanos / 1_000_000_000, nanos % 1_000_000_000)
}

fun millisToHrTime(millis: Long): HrTime {
    val seconds = millis / 1000
    return jsPairOf(seconds, (millis - seconds * 1000) * 1_000_000)
}

fun HrTime.toNanos(): Long {
    return first.toLong()*1_000_000_000L + second.toLong()
}
