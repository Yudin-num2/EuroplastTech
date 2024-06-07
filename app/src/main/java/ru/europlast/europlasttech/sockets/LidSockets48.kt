package ru.europlast.europlasttech.sockets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.europlast.europlasttech.ui.theme.goodSocket

@Composable
fun LidSockets48() {
    val viewModel: SocketViewModel = viewModel()
    val buttonColors by viewModel.buttonColors.collectAsState()
    val socketColors = remember {
        mutableStateListOf<Color>().apply {
            repeat(48) { add(buttonColors["Lid"]?.get(it) ?: goodSocket) }
        }
    }
    var showDialog by remember { mutableStateOf(false) }
    var selectedSocketIndex by remember { mutableStateOf(-1) }

    Box(
        Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp)
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            var socketNumber: Int = 1
            for (i in 0 until 12) {
                Row {
                    for (j in 0 until 4) {
                        val index = (i * 4) + j
                        Button(
                            colors = ButtonDefaults.buttonColors(socketColors[index]),
                            onClick = {
                                selectedSocketIndex = index
                                showDialog = true
                            },
                            modifier = Modifier
                                .padding(2.dp)
                                .weight(1f)
                        ) {
                            Text(text = "$socketNumber")
                        }
                        socketNumber++
                    }
                }
            }
        }
    }
//TODO измени тип приходных данных (цвет)
    if (showDialog) {
        ReasonsPopupForSockets(onDismiss = { showDialog = false }) { chosenColor ->
            socketColors[selectedSocketIndex] = chosenColor
            showDialog = false
            viewModel.updateButtonColor("Lid", selectedSocketIndex, chosenColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LidSocketsPreview() {
    LidSockets48()
}