package dev.coderpwh.sparkapidesk.entity.req

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:44
 * @Description:
 */
@Serializable
data class Payload(
    val message: Message
)

@Serializable
data class Message(
    val text:List<Text>
)

@Serializable
data class Text(
    val role:String,
    val content:String
)
