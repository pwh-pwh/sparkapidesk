package dev.coderpwh.sparkapidesk.entity.req

import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.coderpwh.sparkapidesk.config.ApiConfig
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
) {
    companion object {
        fun fromMessageList(msgList:List<Text>):ReqPayload {
            return ReqPayload(
                header = Header(
                    ApiConfig.config!!.appId,
                    "test"
                ),
                parameter = Parameter(
                    chat = Chat(
                        "generalv3",
                    )
                ),
                payload = Payload(
                    message = Message(
                        text = msgList
                    )
                )
            )
        }
    }
}
