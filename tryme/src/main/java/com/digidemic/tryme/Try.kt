/**
 * TryMe v1.1.0 - https://github.com/Digidemic/try-me
 * (c) 2024 DIGIDEMIC, LLC - All Rights Reserved
 * TryMe developed by Adam Steinberg of DIGIDEMIC, LLC
 * License: Apache License 2.0
 *
 * ====
 *
 * Copyright 2024 DIGIDEMIC, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digidemic.tryme

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A try/catch alternative when you don't need an explicit catch.
 *
 * Replacing try/catch with [Try.me] use cases include (but not limited to):
 *  - When your catch only needs to perform the same redundant task if an exception is thrown (like logging).
 *  - When your catch only needs to return a default value if ever hit.
 *  - When your catch is completely neglected and does nothing.
 *
 * To use, call the static [Try.me] function passing your block of code (and optionally passing a default return value) and TryMe handles the rest.
 *
 * Optional and defined once in your app, define the [Try.GlobalConfig.catchAction] block which is only called when [Try.me] throws
 *  an exception (allowing for the same redundant code to be called anywhere, but only when an exception is thrown).
 */
@OptIn(ExperimentalContracts::class)
object Try {

    object GlobalConfig {
        /**
         * Block of code only called when [Try.me] throws an exception (allowing for the same redundant code to be called everywhere [Try.me] is used).
         *
         * [catchAction] by default does nothing being set to {}.
         * [catchAction] can be updated at anytime but is recommended to
         *  do so as early as possible (like in your main activity's OnCreate()).
         *
         * Reassigning Example: Try.GlobalConfig.catchAction = { Log.d("TryMeCaught", it.message, it) }
         */
        @JvmStatic
        @Volatile
        var catchAction: (Exception) -> Unit = {}
    }

    /**
     * Takes the block of code passed in, [attempt], and invokes it within a try/catch returning type T?.
     *
     * When [attempt] is invoked and DOES NOT throw an exception, the last line of [attempt] will be returned and inferred as
     *  the return type for the Try.me call (even if the return type is [Unit] or a return from Try.me is not intended to be used).
     *
     * When [attempt] is invoked and throws an exception, [GlobalConfig.catchAction] is called first, then null is returned.
     *
     * @param attempt The block of code you wish to have TryMe run and handle exceptions caught.
     * @return Always of type T?. Inferred by [attempt]'s return and always nullable. See overload [me(T, () -> T)] to
     *  enforce a non-nullable return type.
     */
    inline fun <T> me(attempt: () -> T): T? {
        contract { callsInPlace(attempt, InvocationKind.EXACTLY_ONCE) }
        return me(null, attempt)
    }

    /**
     * Takes the block of code passed in, [attempt], and invokes it within a try/catch returning type T.
     *
     * When [attempt] is invoked and DOES NOT throw an exception, [defaultReturnValue]'s type will be inferred as the Try.me return type and
     *  the last line of [attempt] will be returned.
     *
     * When [attempt] is invoked and throws an exception, [GlobalConfig.catchAction] is called first, then the passed [defaultReturnValue] is returned.
     *
     * @param defaultReturnValue Optional, type defaults to inferred type passed in. Value is returned only when Try.me catches an exception.
     * @param attempt The block of code you wish to have TryMe run and handle exceptions caught.
     * @return Always of type [T]. Inferred by [defaultReturnValue]'s type. See overload [me(() -> T)] to
     *  not require a [defaultReturnValue] argument and have the function's return type always be T? (nullable inferred type).
     */
    inline fun <T> me(defaultReturnValue: T, attempt: () -> T): T {
        contract { callsInPlace(attempt, InvocationKind.EXACTLY_ONCE) }
        return try {
            attempt()
        } catch (exception: Exception) {
            GlobalConfig.catchAction(exception)
            defaultReturnValue
        }
    }
}
