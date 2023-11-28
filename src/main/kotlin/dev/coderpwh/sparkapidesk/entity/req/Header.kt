package dev.coderpwh.sparkapidesk.entity.req

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:41
 * @Description:
 */
@Serializable
data class Header(
    @SerialName("app_id")
    val appId:String,
    @SerialName("uid")
    val uid:String
)
