# VelEvents

This repository contains adjusted source code of Velocity Events, a plugin for the Velocity proxy server. 
The original source code can be found [here](https://github.com/PaperMC/Velocity).

## Changes
The original source code has been adjusted to allow for the following features:
- removed Guava dependency;
- Checker Framework annotations replaced with jspecify;
- plugin type made generic;
- unbounded from Velocity API;
- changed package name.

## Dependency
Dependency can be added using JitPack.

Gradle:
```gradle
repositories {
    maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.demkom58.velevents:impl:1.0.0'
}
```

Maven:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.demkom58.velevents</groupId>
    <artifactId>impl</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

There is no difference in usage between the original and adjusted source code, so
the [original documentation](https://docs.papermc.io/velocity/dev/event-api) can be used.

To start using VelEvents, you need to create a new `VelEventManager` and call
`shutdown` on it for graceful shutdown. `EventManager` interface is meant
to be used by plugins to register and unregister event listeners.

## License

Velocity Events api is licensed under the MIT License,
but implementation is licensed under the GNU General Public License v3.0.
