package dev.coderpwh.sparkapidesk.config

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 16:58
 * @Description:
 */
object ApiConfig {
    var config:ApiConfigModel? = null
    //todo
    fun initConfig() {

    }
}

@Serializable
data class ApiConfigModel(
    val appId:String,
    val appSecret:String,
    val appKey:String
)