package dev.coderpwh.sparkapidesk.pojo

import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.coderpwh.sparkapidesk.entity.req.Text
import java.util.Date

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/27 14:04
 * @Description:
 */
data class Message(
    val id: String,
    var content: String,
    var createTime: Date,
    var sender: String,
    var type: MessageType = MessageType.AssistantMessage
)

enum class MessageType {
    SysMessage,
    AssistantMessage
}

fun SnapshotStateList<Message>.toTextList():List<Text> {
    return filter {
        it.type != MessageType.SysMessage
    }.map {
        Text(
            role = it.sender,
            content = it.content
        )
    }
}
