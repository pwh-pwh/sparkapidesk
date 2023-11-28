package dev.coderpwh.sparkapidesk.entity.req

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:43
 * @Description:
 */
@Serializable
data class Parameter(
    val chat: Chat
)

@Serializable
data class Chat(
    val domain:String
)
