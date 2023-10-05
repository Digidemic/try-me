/**
 * Copyright 2023 DIGIDEMIC, LLC
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

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digidemic.tryme.ui.theme.TryMeTheme
import androidx.compose.runtime.setValue
import com.digidemic.tryme.ui.theme.BlueText
import com.digidemic.tryme.ui.theme.GrayText
import com.digidemic.tryme.ui.theme.GreenText
import com.digidemic.tryme.ui.theme.OrangeText
import com.digidemic.tryme.ui.theme.PurpleText
import com.digidemic.tryme.ui.theme.WhiteText

val textModifier = Modifier.padding(4.dp)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sample(this@MainActivity)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Sample(context: Context) {
    val arrayOfStrings = arrayOf("element 0", "element 1")
    var example2Result: String? by remember { mutableStateOf("CHANGE FOR RESULT") }
    var example3Result by remember { mutableStateOf("CHANGE FOR RESULT") }

    //Optional, set once per lifecycle, catchAction empty by default
    Try.GlobalConfig.catchAction = {
        Toast.makeText(context, "Try.me called failed, ${it.message}", Toast.LENGTH_LONG).show()
    }

    TryMeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    SectionTitle("TryMe Demo App")
                    SectionSubtitle("3 interactive examples (scroll below)")
                    SectionSubtitle("Change array indexes to trigger TryMe")
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp)
                ) {
                    FlowRow {
                        Text(text = "val ", color = OrangeText)
                        Text(text = "arr = arrayOf(", color = WhiteText)
                        Text(text = "\"element 0\"", color = GreenText)
                        Text(text = ", ", color = OrangeText)
                        Text(text = "\"element 1\"", color = GreenText)
                        Text(text = ")", color = WhiteText)
                    }
                    Text(text = "")
                    Text(text = "//Optional, set once per lifecycle, called when exception is thrown", color = GrayText)
                    Row {
                        Text(text = "Try.GlobalConfig.", color = WhiteText)
                        Text(text = "catchAction", color = PurpleText)
                        Text(text = " = {", color = WhiteText)
                    }
                    FlowRow(horizontalArrangement = Arrangement.End) {
                        Text(text = "    ")
                        Text(text = "Toast.makeText(", color = WhiteText)
                        Text(text = "this", color = OrangeText)
                        Text(text = ", ", color = OrangeText)
                        Text(text = "Try.me called failed, ", color = GreenText)
                        Text(text = "\${", color = OrangeText)
                        Text(text = "it.", color = WhiteText)
                        Text(text = "message", color = PurpleText)
                        Text(text = "}", color = OrangeText)
                        Text(text = "\"", color = GreenText)
                        Text(text = ", ", color = OrangeText)
                        Text(text = "Toast.", color = WhiteText)
                        Text(text = "LENGTH_SHORT", color = PurpleText)
                        Text(text = ").show()", color = WhiteText)
                    }
                    Row {
                        Text(text = "}", color = WhiteText)
                    }
                    Text(text = "")
                    Row {
                        Text(text = "// Example 1", color = GrayText)
                    }
                    Row {
                        Text(text = "Try.me {", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "    ")
                        Text(text = "val ", color = OrangeText)
                        Text(text = "foo = arr[  ", color = WhiteText)
                        DropDownList(
                            listItems = arrayOf("0", "1", "2"),
                            onSelected = { index ->
                                Try.me {
                                    val foo = arrayOfStrings[index]
                                    Toast.makeText(context, "Success! $foo", Toast.LENGTH_LONG).show()
                                }
                            }
                        )
                        Text(text = "  ]", color = WhiteText)
                    }
                    FlowRow(horizontalArrangement = Arrangement.End) {
                        Text(text = "    ")
                        Text(text = "Toast.makeText(", color = WhiteText)
                        Text(text = "this", color = OrangeText)
                        Text(text = ", ", color = OrangeText)
                        Text(text = "Success! ", color = GreenText)
                        Text(text = "\$", color = OrangeText)
                        Text(text = "foo", color = WhiteText)
                        Text(text = "\"", color = GreenText)
                        Text(text = ", ", color = OrangeText)
                        Text(text = "Toast.", color = WhiteText)
                        Text(text = "LENGTH_SHORT", color = PurpleText)
                        Text(text = ").show()", color = WhiteText)
                    }
                    Text(text = "}", color = WhiteText)
                    Text(text = "")
                    Text(text = "// Example 2", color = GrayText)
                    Row {
                        Text(text = "val ", color = OrangeText)
                        Text(text = "arrValueOrNull: String? =", color = WhiteText)
                    }
                    Row {
                        Text(text = "    ")
                        Text(text = "Try.me {", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "        ")
                        Text(text = "arr[  ", color = WhiteText)
                        DropDownList(
                            listItems = arrayOf("0", "1", "2"),
                            onSelected = { index ->
                                val arrValueOrNull: String? =
                                    Try.me {
                                        arrayOfStrings[index]
                                    }
                                example2Result = arrValueOrNull
                            }
                        )
                        Text(text = "  ]", color = WhiteText)
                    }
                    Row {
                        Text(text = "    ")
                        Text(text = "}", color = WhiteText)
                    }
                    Row {
                        Text(text = "print(arrValueOrDefault) ", color = WhiteText)
                        Text(text = "// $example2Result", color = GrayText)
                    }
                    Text(text = "")
                    Text(text = "// Example 3", color = GrayText)
                    Row {
                        Text(text = "val ", color = OrangeText)
                        Text(text = "arrValueOrDefault: String =", color = WhiteText)
                    }
                    Row {
                        Text(text = "    ")
                        Text(text = "Try.me(", color = WhiteText)
                        Text(text = "defaultReturnValue = ", color = BlueText)
                        Text(text = "\"fail\"", color = GreenText)
                        Text(text = ") {", color = WhiteText)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "        ")
                        Text(text = "arr[  ", color = WhiteText)
                        DropDownList(
                            listItems = arrayOf("0", "1", "2"),
                            onSelected = { index ->
                                val arrValueOrDefault: String =
                                    Try.me(defaultReturnValue = "fail") {
                                        arrayOfStrings[index]
                                    }
                                example3Result = arrValueOrDefault
                            }
                        )
                        Text(text = "  ]", color = WhiteText)
                    }
                    Row {
                        Text(text = "    ")
                        Text(text = "}", color = WhiteText)
                    }
                    Row {
                        Text(text = "print(arrValueOrDefault) ", color = WhiteText)
                        Text(text = "// $example3Result", color = GrayText)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList(
    listItems: Array<String>,
    onSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(listItems[0]) }
    Box(
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listItems.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onSelected(index)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = textModifier
    )
}

@Composable
fun SectionSubtitle(text: String) {
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = textModifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current
    TryMeTheme {
        Sample(context = context)
    }
}