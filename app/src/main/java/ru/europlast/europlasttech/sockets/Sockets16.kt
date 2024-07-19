package ru.europlast.europlasttech.sockets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Sockets16(machineName: String) {
    val viewModel: SocketViewModel = viewModel()
    var showDialog by remember { mutableStateOf(false) }
    var selectedSocketIndex by remember { mutableIntStateOf(1) }
    val isLoading by viewModel.isLoading.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 110.dp)
            .fillMaxSize()
            .background(color = Color.Transparent)
            .verticalScroll(scrollState),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
        } else {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                for (i in 0 until 4) {
                    Row {
                        for (j in 0 until 4) {
                            val index = (i * 4) + j
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    viewModel.getColorForSocket(machineName, index + 1)
                                ),
                                onClick = {
                                    selectedSocketIndex = index + 1
                                    showDialog = true
                                },
                                modifier = Modifier
                                    .padding(2.dp)
                                    .weight(1f)
                            ) {
                                Text(text = "${index + 1}")
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog and !isLoading) {
        ReasonsPopupForSockets(onDismiss = { showDialog = false }) { chosenColor ->
            Log.d("Sockets16 | ReasonsPopupForSockets", "$machineName, $selectedSocketIndex")
            viewModel.updateSocketColor(machineName, selectedSocketIndex, chosenColor)
            showDialog = false
        }
    }
}
