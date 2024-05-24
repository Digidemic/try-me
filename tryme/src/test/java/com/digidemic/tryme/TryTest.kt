/**
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

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TryTest {

    private val mock = mockk<TryTest>()

    /** Default Try.GlobalConfig.catchAction function call */
    private fun defaultFunctionCalledFromGlobalCatchAction() = true

    /** Function with no purpose besides verifying it was called when needed */
    private fun mockFunctionCall() = true

    /** Function to intentionally cause an exception to be thrown*/
    private fun triggerFail() = arrayOf("")[1]

    @Before
    fun setup() {
        mockkStatic(::defaultFunctionCalledFromGlobalCatchAction)
        every { mock.defaultFunctionCalledFromGlobalCatchAction() } returns true
        every { mock.mockFunctionCall() } returns true
        Try.GlobalConfig.catchAction = {
            mock.defaultFunctionCalledFromGlobalCatchAction()
        }
    }

    @After
    fun teardown() {
        Try.GlobalConfig.catchAction = {}
    }

    @Test
    fun `given STR_WORKED, when calling TryMe 3 times, then set all to STR_WORKED`() {
        val setFromWithinTry: String

        Try.me {
            setFromWithinTry = STR_WORKED
        }

        val successValueOrNull: String? = Try.me {
            STR_WORKED
        }

        val successValueOrPassedDefault: String = Try.me(STR_FAILED) {
            STR_WORKED
        }

        assertEquals(STR_WORKED, setFromWithinTry)
        assertEquals(STR_WORKED, successValueOrNull)
        assertEquals(STR_WORKED, successValueOrPassedDefault)
        verify(exactly = 0) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given triggerFail call and STR_WORKED, when calling TryMe 3 times, then set all with default values`() {
        val setFromWithinTry: String

        Try.me {
            triggerFail()
            setFromWithinTry = STR_WORKED
        }

        val successValueOrNull: String? = Try.me {
            triggerFail()
            STR_WORKED
        }

        val successValueOrPassedDefault: String = Try.me(STR_FAILED) {
            triggerFail()
            STR_WORKED
        }

        assertEquals(null, setFromWithinTry)
        assertEquals(null, successValueOrNull)
        assertEquals(STR_FAILED, successValueOrPassedDefault)
        verify(exactly = 3) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given updateOnSuccess starting at 1, when calling Try me 3 times, then updateOnSuccess updates by 1 3 times`() {
        val failed = -5
        var updateOnSuccess = 1

        val setFromWithinTry: Int
        Try.me {
            updateOnSuccess += 1
            setFromWithinTry = updateOnSuccess
        }

        val successValueOrNull: Int? = Try.me {
            updateOnSuccess += 1
            updateOnSuccess
        }

        val successValueOrPassedDefault: Int = Try.me(failed) {
            updateOnSuccess += 1
            updateOnSuccess
        }

        assertEquals(2, setFromWithinTry)
        assertEquals(3, successValueOrNull)
        assertEquals(4, successValueOrPassedDefault)
        assertEquals(4, updateOnSuccess)
        verify(exactly = 0) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given HM_WORKED, when calling TryMe 3 times, then set all to HM_WORKED`() {
        val setFromWithinTry: HashMap<String, Int>

        Try.me {
            setFromWithinTry = HM_WORKED
        }

        val successValueOrNull: HashMap<String, Int>? = Try.me {
            HM_WORKED
        }

        val successValueOrPassedDefault: HashMap<String, Int> = Try.me(HM_FAILED) {
            HM_WORKED
        }

        assertEquals(HM_WORKED, setFromWithinTry)
        assertEquals(HM_WORKED, successValueOrNull)
        assertEquals(HM_WORKED, successValueOrPassedDefault)
        verify(exactly = 0) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given triggerFail call and HM_WORKED, when calling Try me 3 times, then set all with default values`() {
        val setFromWithinTry: HashMap<String, Int>

        Try.me {
            triggerFail()
            setFromWithinTry = HM_WORKED
        }

        val successValueOrNull: HashMap<String, Int>? = Try.me {
            triggerFail()
            HM_WORKED
        }

        val successValueOrPassedDefault: HashMap<String, Int> = Try.me(HM_FAILED) {
            triggerFail()
            HM_WORKED
        }

        assertEquals(null, setFromWithinTry)
        assertEquals(null, successValueOrNull)
        assertEquals(HM_FAILED, successValueOrPassedDefault)
        verify(exactly = 3) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given PAIR_WORKED, when calling Try me 3 times, then set all to PAIR_WORKED`() {
        val setFromWithinTry: Pair<Int, String>

        Try.me {
            setFromWithinTry = PAIR_WORKED
        }

        val successValueOrNull: Pair<Int, String>? = Try.me {
            PAIR_WORKED
        }

        val successValueOrPassedDefault: Pair<Int, String> = Try.me(PAIR_FAILED) {
            PAIR_WORKED
        }

        assertEquals(PAIR_WORKED, setFromWithinTry)
        assertEquals(PAIR_WORKED, successValueOrNull)
        assertEquals(PAIR_WORKED, successValueOrPassedDefault)
        verify(exactly = 0) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given triggerFail call and PAIR_WORKED, when calling Try me 3 times, then set all with default values`() {
        val setFromWithinTry: Pair<Int, String>

        Try.me {
            triggerFail()
            setFromWithinTry = PAIR_WORKED
        }

        val successValueOrNull: Pair<Int, String>? = Try.me {
            triggerFail()
            PAIR_WORKED
        }

        val successValueOrPassedDefault: Pair<Int, String> = Try.me(PAIR_FAILED) {
            triggerFail()
            PAIR_WORKED
        }

        assertEquals(null, setFromWithinTry)
        assertEquals(null, successValueOrNull)
        assertEquals(PAIR_FAILED, successValueOrPassedDefault)
        verify(exactly = 3) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }


    @Test
    fun `given LIST_WORKED, when calling Try me 3 times, then set all to LIST_WORKED`() {
        val setFromWithinTry: List<String>

        Try.me {
            setFromWithinTry = LIST_WORKED
        }

        val successValueOrNull: List<String>? = Try.me {
            LIST_WORKED
        }

        val successValueOrPassedDefault: List<String> = Try.me(LIST_FAILED) {
            LIST_WORKED
        }

        assertEquals(LIST_WORKED, setFromWithinTry)
        assertEquals(LIST_WORKED, successValueOrNull)
        assertEquals(LIST_WORKED, successValueOrPassedDefault)
        verify(exactly = 0) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given triggerFail call and LIST_WORKED, when calling Try me 3 times, then set all with default values type inference`() {
        val setFromWithinTry: List<String>

        Try.me {
            triggerFail()
            setFromWithinTry = LIST_WORKED
        }

        val successValueOrNull = Try.me {
            triggerFail()
            LIST_WORKED
        }

        val successValueOrPassedDefault = Try.me(LIST_FAILED) {
            triggerFail()
            LIST_WORKED
        }

        assertEquals(null, setFromWithinTry)
        assertEquals(null, successValueOrNull)
        assertEquals(LIST_FAILED, successValueOrPassedDefault)
        verify(exactly = 3) { mock.defaultFunctionCalledFromGlobalCatchAction() }
    }

    @Test
    fun `given default exception, updated exception, and empty catchAction, when calling TryMe, then verify calls`() {
        Try.me {
            triggerFail()
        }
        verify(exactly = 1) { mock.defaultFunctionCalledFromGlobalCatchAction() }

        Try.me {
            triggerFail()
        }
        verify(exactly = 2) { mock.defaultFunctionCalledFromGlobalCatchAction() }

        Try.GlobalConfig.catchAction = { mock.mockFunctionCall() }
        Try.me {
            triggerFail()
        }
        verify(exactly = 1) { mock.mockFunctionCall() }

        Try.GlobalConfig.catchAction = { }
        Try.me {
            triggerFail()
        }
        verify(exactly = 2) { mock.defaultFunctionCalledFromGlobalCatchAction() }
        verify(exactly = 1) { mock.mockFunctionCall() }
    }

    @Test
    fun `given nested TryMes, when one call fails, then verify remainder were called`() {
        Try.me {
            mock.mockFunctionCall()
            Try.me {
                mock.mockFunctionCall()
                Try.me {
                    triggerFail()
                    mock.mockFunctionCall()
                }
                Try.me {
                    mock.mockFunctionCall()
                }
            }
        }
        verify(exactly = 3) { mock.mockFunctionCall() }
    }

    private companion object {
        private const val STR_WORKED = "worked"
        private const val STR_FAILED = "failed"
        private val HM_WORKED = hashMapOf("Worked" to 1)
        private val HM_FAILED = hashMapOf("Failed" to 1)
        private val PAIR_WORKED = Pair(1, "Worked")
        private val PAIR_FAILED = Pair(1, "Failed")
        private val LIST_WORKED = listOf("Worked")
        private val LIST_FAILED = listOf("Failed")
    }

}