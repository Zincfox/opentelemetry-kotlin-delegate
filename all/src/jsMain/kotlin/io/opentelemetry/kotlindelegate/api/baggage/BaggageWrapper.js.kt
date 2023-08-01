package io.opentelemetry.kotlindelegate.api.baggage

import io.opentelemetry.kotlindelegate.context.ContextWrapper
import io.opentelemetry.kotlindelegate.context.ImplicitContextKeyedWrapper

actual class BaggageWrapper : ImplicitContextKeyedWrapper(),
        Map<String, BaggageEntryWrapper> {

    actual companion object {

        actual val empty: BaggageWrapper by lazy {
            TODO("Not yet implemented")
        }

        actual fun current(): BaggageWrapper {
            TODO("Not yet implemented")
        }

        actual fun builder(): BaggageBuilderWrapper {
            TODO("Not yet implemented")
        }

        actual fun fromContext(context: ContextWrapper): BaggageWrapper {
            TODO("Not yet implemented")
        }

        actual fun fromContextOrNull(context: ContextWrapper): BaggageWrapper? {
            TODO("Not yet implemented")
        }
    }

    actual fun getEntryValue(entryKey: String): String? {
        TODO("Not yet implemented")
    }

    actual fun toBuilder(): BaggageBuilderWrapper {
        TODO("Not yet implemented")
    }
}
