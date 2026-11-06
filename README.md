# Slice Connector

Slice Connector is a local-first bridge project intended to connect Entry projects with XYOCK.

## Technology layout

- `web/index.html` — minimal HTML shell; replace or redesign it for your own site.
- `web/src/connector.ts` — TypeScript client for the local bridge and message validation.
- `bridge-java/` — Java 17 local HTTP bridge. It binds to loopback only and exposes a small, allowlisted API.
- `native-c/` — C calculation module and hardware abstraction interface. Hardware functions are safe placeholders until a specific supported device is selected.

## Current scope

This is a starter scaffold, not a finished Entry extension. Entry's supported extension mechanism and browser-specific permissions still need to be verified before claiming direct block-to-site launching works.

The Java bridge does not execute shell commands or arbitrary code. It only provides health status and an allowlisted XYOCK launch action.

## Requirements

- Java 17+
- CMake 3.16+
- C11 compiler
- Node.js 20+ (only if you choose to build the TypeScript client)

## Build the C module

```sh
cmake -S native-c -B native-c/build
cmake --build native-c/build
```

## Run the Java bridge

```sh
cd bridge-java
./gradlew run
```

A Gradle wrapper will be added when the build setup is finalized; alternatively import the Gradle project in an IDE.

## Security notes

- The bridge listens only on `127.0.0.1`.
- Do not expose its port to your local network or the public internet.
- Do not add arbitrary command execution endpoints.
- Hardware access must be implemented for a named device and tested with safe limits.
