package dev.coderpwh.sparkapidesk.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 15:55
 * @Description:
 */

@Composable
fun About(isShow:Boolean,onCloseRequest:()->Unit) {
    Dialog(
        visible = isShow,
        onCloseRequest = onCloseRequest,
        title = "About",
        state = rememberDialogState(size = DpSize(400.dp, 300.dp)),
    ) {
        Column {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Image(painter = painterResource("img.png"), contentDescription = "img", modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "spark apidesk", modifier = Modifier.align(alignment = Alignment.CenterVertically))
            }
            //显示简介
            TextField(value = "使用sparkapi进行接口对接的桌面端应用", onValueChange = {}, readOnly = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),
                )
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Author: coderpwh", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(20.dp))
                LinkButton("https://github.com/pwh-pwh","github")
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Code: sparkapidesk", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(20.dp))
                LinkButton("https://github.com/pwh-pwh/sparkapidesk","sparkapidesk")
            }
        }

    }
}