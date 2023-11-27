package dev.coderpwh.sparkapidesk.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.rememberDialogState

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 16:43
 * @Description:
 */

@Composable
fun KeyConfigDialog(alertDialog:Boolean,onCloseRequest:()->Unit,trayState: TrayState) {
    Dialog(
        onCloseRequest = onCloseRequest,
        visible = alertDialog,
        state = rememberDialogState(size = DpSize(400.dp, 300.dp)),
        title = "set key"
    ) {
        KeyForm(trayState)
    }
}