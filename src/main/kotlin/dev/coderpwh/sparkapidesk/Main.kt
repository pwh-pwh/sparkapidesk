package dev.coderpwh.sparkapidesk

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import dev.coderpwh.sparkapidesk.components.About
import dev.coderpwh.sparkapidesk.components.KeyConfigDialog
import dev.coderpwh.sparkapidesk.screen.ChatScreen

@Composable
@Preview
fun App() {

    MaterialTheme {
        ChatScreen()
    }
}

fun main() = application {
    var windowShow by remember { mutableStateOf(true) }
    Window(onCloseRequest = { windowShow = false },
        visible = windowShow,
        icon = painterResource("img.png"),
        title = "spark apidesk",) {
        var alertDialog by rememberSaveable{
            mutableStateOf(false)
        }
        var aboutDialog by rememberSaveable{
            mutableStateOf(false)
        }

        val trayState = rememberTrayState()

        KeyConfigDialog(alertDialog,{
            alertDialog = false
        },trayState)
        About(aboutDialog) { aboutDialog = false }
        MenuBar {
            Menu("me") {
                Item("set key") {
                    alertDialog = true
                }
            }
            Menu("help") {
                Item("about") {
                    aboutDialog = true
                }
            }
        }
        Tray(
            icon = painterResource("img.png"),
            menu = {
                Item("exit", onClick = ::exitApplication)
            },
            onAction = {
                windowShow = true
            },
            tooltip = "spark apidesk",
            state = trayState
        )
        App()
    }
}
