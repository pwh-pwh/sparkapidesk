package dev.coderpwh.sparkapidesk.config

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 16:58
 * @Description:
 */
object ApiConfig {
    var config: ApiConfigModel? = null

    fun initConfig() {
        File("config.json").let {
            if (it.exists()) {
                it.readText().let { jsonStr ->
                    config = Json.decodeFromString(jsonStr)
                }
            }
        }
    }

    fun writeConfig(config: ApiConfigModel) {
        File("config.json").let {
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