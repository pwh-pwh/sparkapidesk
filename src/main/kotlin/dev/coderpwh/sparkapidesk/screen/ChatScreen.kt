package dev.coderpwh.sparkapidesk.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.coderpwh.sparkapidesk.client.SparkApiClient
import dev.coderpwh.sparkapidesk.pojo.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * @Auther: pangwenhao
 * @Date: 2023/11/24 17:12
 * @Description:
 */

@Composable
@Preview
fun ChatScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        var msgList: SnapshotStateList<Message> = remember {
            mutableStateListOf(
            )
        }
        var textFieldState by remember {
            mutableStateOf("")
        }
        var scope = rememberCoroutineScope()
        var rememberLazyListState = rememberLazyListState()
        ShowMessage(msgList, modifier = Modifier.align(Alignment.TopCenter)
            .fillMaxHeight(0.85f),state = rememberLazyListState)
        Row(modifier = Modifier.padding(8.dp)
            .align(Alignment.BottomCenter).fillMaxWidth()) {

            // 输入框
            OutlinedTextField(
                value = textFieldState,
                onValueChange = { textFieldState = it },
                label = { Text("Message") },
                modifier = Modifier.weight(1f).onPreviewKeyEvent {
                    when {
                        (it.key == Key.Enter && it.type == KeyEventType.KeyDown) -> {
                            msgList.add(Message(UUID.randomUUID().toString(), textFieldState, Date(), "user"))
                            textFieldState = ""
                            scope.launch {
                                try {
                                    SparkApiClient().simpleChat(msgList)
                                    rememberLazyListState.animateScrollToItem(rememberLazyListState.firstVisibleItemIndex + 2)
                                } catch (e: Exception) {
                                    msgList.add(Message(UUID.randomUUID().toString(), e.toString(), Date(), "assistant"))
                                }
                            }
                            true
                        }
                        else -> false
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // 发送按钮
            Button(onClick = {
                msgList.add(Message(UUID.randomUUID().toString(), textFieldState, Date(), "user"))
                textFieldState = ""
                scope.launch {
                    try {
                        SparkApiClient().simpleChat(msgList)
                        rememberLazyListState.animateScrollToItem(rememberLazyListState.firstVisibleItemIndex + 2)
                    } catch (e: Exception) {
                        msgList.add(Message(UUID.randomUUID().toString(), e.toString(), Date(), "assistant"))
                    }
                }
            }, modifier = Modifier.height(60.dp).padding(top = 9.dp)) {
                Text("Send")
            }
        }

    }

}

@Composable
fun ShowMessage(msgList:List<Message>,modifier: Modifier,state:LazyListState) {
    LazyColumn(
        modifier = modifier, state = state
    ) {
        items(items = msgList) {
            msg ->
            if (msg.sender == "assistant") {
                MessageLeft(msg)
            } else {
                MessageRight(msg)
            }
        }
    }
}

@Composable
fun MessageLeft(msg:Message) {
    Row(
        modifier = Modifier.padding(all = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Image(
            painter = painterResource("gpt.png"),
            contentDescription = "gpt头像",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        var isExpanded by remember {
            mutableStateOf(true)
        }
        Column {
            Text(text = msg.sender,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shape = RectangleShape,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = msg.content,
                        modifier = Modifier
                            .selectable(true) {

                            }
                            .clickable {
                                isExpanded = !isExpanded
                            },
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}

@Composable
fun MessageRight(msg:Message) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = msg.sender,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(10.dp))
            var isExpanded by remember {
                mutableStateOf(false)
            }
            Surface(
                shape = RectangleShape,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = msg.content,
                        modifier = Modifier
                            .clickable {
                                isExpanded = !isExpanded
                            },
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource("user1.jpg"),
            contentDescription = "user头像",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colors.primary, CircleShape)
        )
    }
}