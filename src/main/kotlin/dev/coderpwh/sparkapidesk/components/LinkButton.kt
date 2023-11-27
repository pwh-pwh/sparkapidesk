package dev.coderpwh.sparkapidesk.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import java.awt.Desktop
import java.net.URI

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 16:09
 * @Description:
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LinkButton(url:String,title:String) {
    Surface(
        onClick = {
            Desktop.getDesktop().browse(URI(url))
        }
    ) {
        val text = buildAnnotatedString {
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(title)
            }
        }
        //添加链接样式
        Text(text = text,
            modifier = Modifier.padding(10.dp)
        )
    }
}