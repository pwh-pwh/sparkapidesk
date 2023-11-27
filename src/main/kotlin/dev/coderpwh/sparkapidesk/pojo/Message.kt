package dev.coderpwh.sparkapidesk.pojo

import java.util.Date

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 14:04
 * @Description:
 */
data class Message(
    val id: String,
    val content: String,
    val createTime: Date,
    val sender: String
)
