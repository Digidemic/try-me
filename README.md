# TryMe

## A try/catch alternative when you don't need an explicit catch. 
#### We've all been there, neglecting the `catch` in `try/catch`. Maybe your use case only needs the same redundant task if an exception is thrown (like logging). Or maybe your catch only needs to return a default value if ever hit. Or maybe your catch... does absolutely nothing. For all these cases, TryMe is the perfect alternative. TryMe allows for less code to be written, and less redundant code to be added.
<br>

<table>
<tr>
<th>Before when using Try/Catch</th>
<th>Try.me (Equivalent to Try/Catch)</th>
</tr>
<tr>
<td>
  
```kotlin
val arr = arrayOf("test", "array")

//Ex 1: try code, no return
try {
    val elm = arr[5]
    exampleFun(elm)
} catch (e: Exception) {
    Log.d("Tag", e.message, e)
}

//Ex 2: set from array or null
val arrValueOrNull: String? =
    try {
        arr[5]
    } catch (e: Exception) {
        Log.d("Tag", e.message, e)
        null
    }

//Ex 3: set from array or "fail"
val arrValueOrDefault: String =
    try {
        arr[3]
    } catch (e: Exception) {
        Log.d("Tag", e.message, e)
        "fail"
    }
```
  
</td>
<td>

```kotlin
val arr = arrayOf("test", "array")

//Optional, set once, catchAction empty by default
Try.GlobalConfig.catchAction = {
    Log.d("Tag", it.message, it)
}

//Ex 1: try code, no return
Try.me {
    val elm = arr[5]
    exampleFun(elm)
}

//Ex 2: set from array or null
val arrValueOrNull: String? =
    Try.me {
        arr[5]
    }

//Ex 3: set from array or "fail"
val arrValueOrDefault: String =
    Try.me(defaultReturnValue = "fail") {
        arr[5]
    }



```

</td>
</tr>
</table>

### For "I don't care that an exception was thrown, just keep going" uses.
- No setup code required before using TryMe. Just call the static [Try.me()](#examples) function with your block of code and TryMe handles the rest! 
  - Optionally, a [defaultReturnValue](#main-syntax) argument can be included where that value is returned only if an exception is caught.
- Rather than a mandatory catch block, optionally and just once, create a global catch block (see [GlobalConfig](#global-settings)) that will only be called when TryMe throws an exception anywhere in your app!
- No code compromise using TryMe over the standard `try`. The same code that can handled in a `try` can be handled in TryMe!

### Check out the [example app for TryMe](/example-tryme/)!

<br>

## Table of Contents
- [Examples](#examples)
- [Syntax](#syntax)
- [Installation](#installation)
- [Versioning](#versioning)
- [License](#license)
  <br><br>

## Examples

### Example 1: No Return, just TryMe!

The following tries to take a value from the String array, `arr`, and pass it to a function.
If an `ArrayIndexOutOfBoundsException` is thrown, `exampleFun()` is not called, [Try.GlobalConfig.catchAction](#global-settings) is called, then the program continues without crashing.
```kotlin
Try.me {
    val elm = arr[5]
    exampleFun(elm)
}
```
<br>

### Example 2: Set value or null

The following tries to take a value from the String array, `arr`, to return it and set `arrValueOrNull` with that value. Since [defaultReturnValue](#main-syntax) was not passed into TryMe, if an `ArrayIndexOutOfBoundsException` is thrown, [Try.GlobalConfig.catchAction](#global-settings) is called first, then `null` will be returned from TryMe setting `arrValueOrNull`.
```kotlin
val arrValueOrNull: String? =
    Try.me {
        arr[5]
    }
```
<br>

### Example 3: Set value or default

The following tries to take a value from the String array, `arr`, to return it and set `arrValueOrDefault` with that value. Because a non-null [defaultReturnValue](#main-syntax) was passed in, the set type for `arrValueOrDefault` is never null. If an `ArrayIndexOutOfBoundsException` is thrown, [Try.GlobalConfig.catchAction](#global-settings) is called first, then the passed value `"failed"` will be returned from TryMe setting `arrValueOrDefault`.
```kotlin
val arrValueOrDefault: String =
    Try.me(defaultReturnValue = "failed") {
        arr[5]
    }
```
<br>

## Syntax

### Main Syntax

Usage is simply calling the static [Try.me()](#examples) function passing your block of code (and optionally a default return value):

- <b>`defaultReturnValue`</b> - T / <i><b>Optional</b></i> (Defaults to inferred type passed in. T? if undefined)
    - When an exception is caught calling Try.me, [Try.GlobalConfig.catchAction](#global-settings) is always called first, then the passed `defaultReturnValue` will be returned. When undefined, null is returned which also changes the TryMe return type to nullable.
- <b>`attempt`</b> - () -> T / <i><b>Required</b></i>
    - The block of code you wish to have TryMe run and handle exceptions caught. Same code can be used here as would in a standard `try`.


See [Examples](#examples) section for code using these arguments.

### Global Settings
`Try.GlobalConfig` has all the public configurable settings (currently just one) that can be updated anytime during runtime. If any are intended to be updated, it is recommended to do so as early as possible (like in your main activity's `OnCreate()`).

The following are the settings set to their default values.
- ```kotlin
  /**
   * Only called when Try.me throws an exception (allowing for the same redundant code to be called everywhere Try.me is used).
   * By default does nothing.
   * Reassigning Example: Try.GlobalConfig.catchAction = { Log.d("TryMeCaught", it.message, it) }
   */
  Try.GlobalConfig.catchAction = {}
  ```
<br>

## Installation

### Install with JitPack
[![](https://jitpack.io/v/Digidemic/try-me.svg)](https://jitpack.io/#Digidemic/try-me)
1) Add JitPack to your project's root `build.gradle` at the end of `repositories`:
- ```groovy
  dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
          mavenCentral()
          maven { url 'https://jitpack.io' }
    }
  }
  ```
2) In the `build.gradle` of the module(s) you wish to use TryMe with, add the following to `dependencies`:
- ```groovy
  dependencies {
      // Required: Installs the .aar without any documentation.
      implementation 'com.github.digidemic:try-me:1.1.0'
      
      // Optional: Displays documentation while writing coding. 
      implementation 'com.github.digidemic:try-me:1.1.0:javadoc'

      // Optional: Displays documentation (more comprehensive than javadoc in some cases) and uncompiled code when stepping into library.
      implementation 'com.github.digidemic:try-me:1.1.0:sources'
  }
  ```
3) [Sync gradle](https://www.delasign.com/blog/how-to-sync-an-android-project-with-its-gradle-files-in-android-studio/) successfully.
4) Done! Your Android project is now ready to use TryMe. Go to [Examples](#examples) or [Syntax](#syntax) for TryMe usage!

<br>

## Versioning
- [SemVer](http://semver.org/) is used for versioning.
- Given a version number MAJOR . MINOR . PATCH
    1) MAJOR version - Incompatible API changes.
    2) MINOR version - Functionality added in a backwards-compatible manner.
    3) PATCH version - Backwards-compatible bug fixes.
       <br><br>

## License
Try Me created by Adam Steinberg of DIGIDEMIC, LLC
```
Copyright 2024 DIGIDEMIC, LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```