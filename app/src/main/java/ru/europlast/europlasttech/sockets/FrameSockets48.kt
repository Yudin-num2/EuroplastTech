package ru.europlast.europlasttech.sockets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.europlast.europlasttech.ui.theme.Underfilling
import ru.europlast.europlasttech.ui.theme.goodSocket

@Composable
fun FrameSockets48() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 30.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            var socketNumber: Int = 1
            for (i in 0 until 12) {
                Row {
                    for (j in 0 until 4) {
                        Button(
                            colors = ButtonDefaults.buttonColors(Underfilling),
                            onClick = { /* Handle button click */ },
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
}

@Preview(showBackground = true)
@Composable
fun FrameSocketsPreview(){
    FrameSockets48()
}