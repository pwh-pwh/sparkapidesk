package dev.coderpwh.sparkapidesk.entity.req

import kotlinx.serialization.Serializable

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:46
 * @Description:
 */
@Serializable
data class ReqPayload(
    val header: Header,
    val parameter: Parameter,
    val payload: Payload
)
