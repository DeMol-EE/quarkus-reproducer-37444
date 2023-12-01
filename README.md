# Reproducer for [#37444](https://github.com/quarkusio/quarkus/issues/37444)

This is a reproducer for an issue with injecting an `Optional<List<String>>` configuration property in combination with Quarkus' native compilation.

## Working flow

1. `mvn quarkus:dev`
2. `curl http://localhost:8080/hello`
3. Receive response "Hello"

## Broken flow

1. `docker build -t reproducer-37444 .`
2. `docker run --rm -p 8080:8080 -e STUFF='b,c' reproducer-37444`
3. `curl http://localhost:8080/hello`
4. Observe logs to see empty optional (if injected)
