package dev.coderpwh.sparkapidesk.service

import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.coderpwh.sparkapidesk.pojo.Message
import dev.coderpwh.sparkapidesk.pojo.MessageType
import java.util.*

/**
 * @Auther: pangwenhao
 * @Date: 2023/12/8 16:40
 * @Description:
 */
class CommandService {
    fun doDispatch(msgList: SnapshotStateList<Message>):Boolean {
        val last = msgList.last()
        return when (last.content) {
            "/clear" -> {
                msgList.clear()
                true
            }
            "/help" -> {
                last.type = MessageType.SysMessage
                msgList.add(Message(UUID.randomUUID().toString(),"/clear: 清空聊天记录\n/help: 帮助", Date(),"assistant",MessageType.SysMessage))
                true
            }
            else -> {
                false
            }
        }
    }
}