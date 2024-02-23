package dev.coderpwh.sparkapidesk.client

import androidx.compose.runtime.snapshots.SnapshotStateList
import dev.coderpwh.sparkapidesk.config.ApiConfig
import dev.coderpwh.sparkapidesk.entity.req.*
import dev.coderpwh.sparkapidesk.entity.resp.RespPayload
import dev.coderpwh.sparkapidesk.pojo.toTextList
import dev.coderpwh.sparkapidesk.service.CommandService
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/28 09:33
 * @Description:
 */
class SparkApiClient(val cmdService: CommandService) {
    val client = HttpClient {
        install(WebSockets)
    }

    companion object {
        val host = "spark-api.xf-yun.com"
        val v3ApiPath = "/v3.5/chat"
        val json = Json {
            ignoreUnknownKeys = true
        }

        fun encodeParams(params: Map<String, String>): String {
            return params.entries.joinToString("&") {
                URLEncoder.encode(it.key, StandardCharsets.UTF_8.toString()) + "=" + URLEncoder.encode(
                    it.value,
                    StandardCharsets.UTF_8.toString()
                )
            }
        }


        fun getDate(): String {
            return SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US).let {
                it.timeZone = TimeZone.getTimeZone("GMT")
                it.format(Date())
            }
        }

        fun getAuthrization(): String {
            ApiConfig.initConfig()
            val date = getDate()
            var preStr = "host: $host\ndate: ${date}\nGET $v3ApiPath HTTP/1.1"
            val mac = Mac.getInstance("hmacsha256")
            val spec = SecretKeySpec(ApiConfig.config!!.appSecret.toByteArray(), "hmacsha256")
            mac.init(spec)
            val hexDigits = mac.doFinal(preStr.toByteArray())
            val sha = Base64.getEncoder().encodeToString(hexDigits)
            val authorization = String.format(
                "api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"",
                ApiConfig.config!!.appKey,
                "hmac-sha256",
                "host date request-line",
                sha
            )
            return encodeParams(
                mapOf(
                    "authorization" to Base64.getEncoder().encodeToString(authorization.toByteArray()),
                    "date" to date,
                    "host" to SparkApiClient.host
                )
            )
        }
    }

    suspend fun chat(req: ReqPayload): RespPayload? {
        var resp: RespPayload? = null
        client.webSocket(
            method = HttpMethod.Get,
            host = "${SparkApiClient.host}",
            path = v3ApiPath + "?" + getAuthrization(),
        ) {
            var encodeToString = json.encodeToString(req)
            send(encodeToString.toByteArray())
            while (true) {
                println("into")
                val message = incoming.receive() as? Frame.Text ?: continue
                println("message:${message.readText()}")
                resp = json.decodeFromString<RespPayload>(message.readText())
                if (resp!!.header.status == 2) {
                    println("break")
                    break
                }
            }
        }
        client.close()
        return resp
    }

    suspend fun simpleChat(msgList:SnapshotStateList<dev.coderpwh.sparkapidesk.pojo.Message>){
        if (cmdService.doDispatch(msgList)) {
            return
        }
        client.webSocket(
            method = HttpMethod.Get,
            host = "${SparkApiClient.host}",
            path = v3ApiPath + "?" + getAuthrization(),
        ) {
            var reqJson = json.encodeToString(ReqPayload.fromMessageList(msgList.toTextList()))
            send(reqJson.toByteArray())
            while (true) {
                val message = incoming.receive() as? Frame.Text ?: continue
                println(message.readText())
                val resp = json.decodeFromString<RespPayload>(message.readText())
                if(msgList.last().id == resp.header.sid) {
                    var last = msgList.last()
                    msgList.removeLast()
                    last.content += resp.payload.choices.text[0].content
                    msgList.add(last)
                } else {
                    val msg = dev.coderpwh.sparkapidesk.pojo.Message(
                        resp.header.sid,
                        resp.payload.choices.text[0].content,
                        Date(),
                        "assistant"
                    )
                    msgList.add(msg)
                }
                if(resp.header.status == 2) {
                    break
                }
            }
        }
    }


}

fun main() {
    ApiConfig.initConfig()
    var sparkApiClient = SparkApiClient(CommandService())
    var req = ReqPayload(
        header = Header(
            ApiConfig.config!!.appId,
            "test"
        ),
        parameter = Parameter(
            chat = Chat(
                "generalv3.5",
            )
        ),
        payload = Payload(
            message = Message(
                text = listOf(
                    Text(
                        role = "user",
                        content = "介绍你自己和你的模型版本"
                    )
                )
            )
        )
    )
    runBlocking {
        var resp = sparkApiClient.chat(req)
        println(resp)
    }
}