package dev.coderpwh.sparkapidesk.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.rememberNotification
import dev.coderpwh.sparkapidesk.config.ApiConfig
import dev.coderpwh.sparkapidesk.config.ApiConfigModel

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 17:02
 * @Description:
 */
@Composable
fun KeyForm(trayState:TrayState) {
    var appId by remember {
        mutableStateOf("")
    }
    var appKey by remember {
        mutableStateOf("")
    }
    var appSecret by remember {
        mutableStateOf("")
    }
    val errNotification = rememberNotification(
        "错误提示",
        "请检查appId，appKey，appSecret是否为空",
        Notification.Type.Info
    )
    val sucNotification = rememberNotification(
        "设置成功",
        "成功设置api接口认证信息,配置文件保存config.json",
        Notification.Type.Info
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = appId,
            onValueChange = { value ->
                appId = value
            },
            label = { Text(text = "appId") },
            placeholder = { Text(text = "Enter your appId") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        // appKey TextField
        OutlinedTextField(
            value = appKey,
            onValueChange = { value ->
                appKey = value
            },
            label = { Text(text = "appKey") },
            placeholder = { Text(text = "Enter your appKey") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = appSecret,
            onValueChange = { value ->
                appSecret = value
            },
            label = { Text(text = "appSecret") },
            placeholder = { Text(text = "Enter your appSecret") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        // Submit Button
        Button(
            onClick = {
                      //检查appId,appKey,appSecret是否为空
                      if (appId.isEmpty() || appKey.isEmpty() || appSecret.isEmpty()) {
                        trayState.sendNotification(errNotification)
                      } else {
                          trayState.sendNotification(sucNotification)
                          ApiConfig.config = ApiConfigModel(appId,appKey,appSecret)
                          ApiConfig.writeConfig(ApiConfig.config!!)
                      }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set")
        }
    }
}