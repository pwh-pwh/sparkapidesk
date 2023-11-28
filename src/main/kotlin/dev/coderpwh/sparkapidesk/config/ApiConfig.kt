package dev.coderpwh.sparkapidesk.config

import androidx.compose.ui.text.toLowerCase
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.isDirectory
import kotlin.io.path.name

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 16:58
 * @Description:
 */
object ApiConfig {
    var config: ApiConfigModel? = null

    val filePath = if (System.getProperty("os.name").lowercase(Locale.getDefault()).contains("win")) {
        "config.json"
    } else {
        System.getProperty("user.home") +"/Library/Application Support"+"/config.json"
    }

    fun initConfig() {
        File(filePath).let {
            if (it.exists()) {
                it.readText().let { jsonStr ->
                    config = Json.decodeFromString(jsonStr)
                }
            }
        }
    }

    fun writeConfig(config: ApiConfigModel) {
        File(filePath).let {
            it.writeText(Json.encodeToString(config))
        }
    }
}

@Serializable
data class ApiConfigModel(
    val appId: String,
    val appSecret: String,
    val appKey: String
)