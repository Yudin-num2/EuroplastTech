package ru.europlast.europlasttech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.wear.compose.material.dialog.Dialog
import ru.europlast.europlasttech.ui.theme.WhiteTransparent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()

        }
    }
}

@Preview
@Composable
fun MainScreen() {

    var isDialogVisible by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_img),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(WhiteTransparent),
            ) {
                Text(
                    text = stringResource(id = R.string.CurrentTasks), style = TextStyle(
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                )
            }
            Button(
                onClick = {
                          isDialogVisible = !isDialogVisible
                },
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(WhiteTransparent),
            ) {
                Text(
                    text = stringResource(id = R.string.Sockets), style = TextStyle(
                        textAlign = TextAlign.Center, fontSize = 26.sp, color = Color.Black
                    )
                )
            }
//            if (isDialogVisible) {
//                CustomDialog(
//                    onDismiss = { isDialogVisible = false }
//                )
//            }
            }
            Button(
                onClick = { /* Действие при нажатии кнопки */ },
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(WhiteTransparent),
            ) {
                Text(
                    text = stringResource(id = R.string.register_a_defect), style = TextStyle(
                        textAlign = TextAlign.Center, fontSize = 26.sp, color = Color.Black
                    )
                )
            }
            Button(
                onClick = { /* Действие при нажатии кнопки */ },
                modifier = Modifier
                    .padding(horizontal = 40.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(10.dp),
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(WhiteTransparent),
            ) {
                Text(
                    text = stringResource(id = R.string.register_a_anomaly), style = TextStyle(
                        textAlign = TextAlign.Center, fontSize = 26.sp, color = Color.Black
                    )
                )
            }

        }
    }
}
//@Composable
//fun CustomDialog(onDismiss: () -> Unit) {
//    Dialog(
//        onDismissRequest = onDismiss,
//        properties = DialogProperties(
//            showDialog = true,
//            dismissOnBackPress = true,
//            dismissOnClickOutside = true
//        )) {
//        Box(
//            modifier = Modifier
//                .background(Color.Black.copy(alpha = 0.5f))
//                .fillMaxSize()
//                .clickable { onDismiss() }
//        )
//
//        Box(
//            modifier = Modifier
//                .padding(16.dp)
//                .background(Color.White)
//                .clip(RoundedCornerShape(8.dp))
//        ) {
//            // Здесь можно разместить содержимое вашего диалогового окна с кнопками
//            // Например, кнопки и другие элементы интерфейса
//        }
//    }
//}