# jvm65
[![Build Status](https://www.travis-ci.com/johnnystarr/jvm65.svg?branch=main)](https://www.travis-ci.com/johnnystarr/jvm65)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=johnnystarr_jvm65&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=johnnystarr_jvm65)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=johnnystarr_jvm65&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=johnnystarr_jvm65)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=johnnystarr_jvm65&metric=bugs)](https://sonarcloud.io/dashboard?id=johnnystarr_jvm65)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=johnnystarr_jvm65&metric=ncloc)](https://sonarcloud.io/dashboard?id=johnnystarr_jvm65)
[![codecov](https://codecov.io/gh/johnnystarr/jvm65/branch/main/graph/badge.svg?token=0R7WKAOCSG)](https://codecov.io/gh/johnnystarr/jvm65)
![Maven Central](https://img.shields.io/maven-central/v/io.johnnystarr/jvm65)
![jvm65-logo](doc/jvm65.png)

A JVM library that provides a 6502 simulator.

### Implementation
jvm65 is written in Kotlin but is interoperable with any JVM language.

### Purpose
I have always loved the 6502 processor.  Although there are plenty of simulators out there, I wanted to target the JVM for the sake of curiosity and learning.

### Use Cases

- 6502 centric emulators
- Embedded systems simulation

### Code Coverage
![coverage-report](https://codecov.io/gh/johnnystarr/jvm65/branch/main/graphs/sunburst.svg)

### Leverage in Gradle Project
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.johnnystarr:jvm65:0.1.2'
}
```

### Leverage in Maven Project
```xml
<dependency>
  <groupId>io.johnnystarr</groupId>
  <artifactId>jvm65</artifactId>
  <version>0.1.2</version>
</dependency>
```