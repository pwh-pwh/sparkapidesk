package dev.coderpwh.sparkapidesk.config

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

data class ApiConfigModel(
    val appId:String,
    val appSecret:String,
    val appKey:String
)