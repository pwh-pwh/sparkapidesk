package dev.coderpwh.sparkapidesk.entity.resp

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:47
 * @Description:
 */
@Serializable
data class Header(
    val code:Int,
    val message:String,
    val sid:String,
    val status:Int
)
