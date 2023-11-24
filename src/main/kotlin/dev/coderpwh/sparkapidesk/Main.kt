package dev.coderpwh.sparkapidesk

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {

        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        var alertDialog by rememberSaveable{
            mutableStateOf(false)
        }
        Dialog(
            onCloseRequest = {
                alertDialog = false
            },
            visible = alertDialog,
            state = rememberDialogState(size = DpSize(400.dp, 300.dp)),
            title = "set key"
        ) {
            Text("dialog demo")
        }
        MenuBar {
            Menu("me") {
                Item("set key") {
                    alertDialog = true
                }
            }
            Menu("help") {
                Item("about") {

                }
            }
        }
        App()
    }
}
