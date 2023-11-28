package dev.coderpwh.sparkapidesk.entity.resp

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:48
 * @Description:
 */
@Serializable
data class Payload(
    val choices:Choices,
)

@Serializable
data class Choices(
    val status:Int,
    val seq:Int,
    val text:List<Text>
)

@Serializable
data class Text(
    val content:String,
    val role:String,
    val index:Int
)
