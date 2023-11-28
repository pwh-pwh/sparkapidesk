package dev.coderpwh.sparkapidesk.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 16:43
 * @Description:
 */

@Composable
fun KeyConfigDialog(alertDialog:Boolean,onCloseRequest:()->Unit,trayState: TrayState) {
    DialogWindow(onCloseRequest = onCloseRequest,
        state = rememberDialogState(size = DpSize(400.dp, 300.dp)),
        visible = alertDialog,
        title = "set key",
         ) {
        KeyForm(trayState)
    }
}