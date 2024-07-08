# Opentelemetry kotlin delegate

## About

A simple opentelemetry wrapper library which delegates to the JS- and Java-specific opentelemetry libraries under the hood.

The benefit of this approach over [dcxp/opentelemetry-kotlin](https://github.com/dcxp/opentelemetry-kotlin) is that it allows the interop with the target-specific libraries, whereas opentelemetry-kotlin reimplements the opentelemetry specification, resulting in its own classes and no interop with libraries not depending on it.

On java no wrapper objects are allocated, nearly everything is done with the actual OpenTelemetry library classes by utilizing `actual typealias`.

## TODO

- Documentation
- Publish official maven artifact and move to public github repository
