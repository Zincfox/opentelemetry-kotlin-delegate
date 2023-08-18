# Opentelemetry kotlin delegate

## About

A simple opentelemetry wrapper library which delegates to the ~~js-~~ (TODO) and java-specific opentelemetry libraries under the hood.

The benefit of this approach over [dcxp/opentelemetry-kotlin](https://github.com/dcxp/opentelemetry-kotlin) is that it allows the interop with the target-specific libraries, wheras opentelemetry-kotlin reimplements the opentelemetry specification, resulting in its own classes and no interop with libraries not depending on it.

## TODO

- Support javascript
- Documentation
- Publish official maven artifact and move to public github repository
