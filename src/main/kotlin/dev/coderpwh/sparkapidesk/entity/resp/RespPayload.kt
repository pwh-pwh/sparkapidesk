package dev.coderpwh.sparkapidesk.entity.resp

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 10:03
 * @Description:
 */
@Serializable
data class RespPayload(
    val header:Header,
    val payload:Payload
)
