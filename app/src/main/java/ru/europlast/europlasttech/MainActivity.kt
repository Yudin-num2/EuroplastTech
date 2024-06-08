package ru.europlast.europlasttech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.europlast.europlasttech.ui.theme.WhiteTransparent


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()

        }
    }
}
@Preview
@Composable
fun MainScreen(navController: NavController = rememberNavController()
) {

    var isDialogVisible by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_img),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row {
                Button(
                    onClick = { navController.navigate(Screens.CurrentTasksScreen.route) },
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
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
            Row {
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
                            textAlign = TextAlign.Center, fontSize = 26.sp, color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }


            Row {
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
                            textAlign = TextAlign.Center, fontSize = 26.sp, color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
            Row {
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
                            textAlign = TextAlign.Center, fontSize = 26.sp, color = Color.Black,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    }
    if(isDialogVisible){
        CustomDialog(onDismiss = { isDialogVisible = false }, navController = navController)
    }
}

@Composable
fun CustomDialog(
    onDismiss:()->Unit,
    navController: NavController,
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .border(1.dp, color = Color.Black, shape = RoundedCornerShape(15.dp)),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Spacer to push the text to the center
                    Spacer(modifier = Modifier.weight(2f))

                    Text(
                        text = stringResource(R.string.choose_machine),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.zIndex(1f)
                    )

                    // Spacer to push the icon to the right edge
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close Dialog",
                        tint = colorResource(android.R.color.darker_gray),
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .width(30.dp)
                            .height(30.dp)
                            .clickable { onDismiss() }

                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screens.SocketsT01Screen.route) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 20.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 1",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
                Button(
                    onClick = { navController.navigate(
                        Screens.SocketsT02_7_Screen.route.replace(
                            "{machine_name}", "Telerobot 2")) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 2",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ))
                }
                Button(
                    onClick = { navController.navigate(
                        Screens.SocketsT02_7_Screen.route.replace(
                            "{machine_name}", "Telerobot 3")) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 3",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ))
                }
                Button(
                    onClick = { navController.navigate(
                        Screens.SocketsT02_7_Screen.route.replace(
                            "{machine_name}", "Telerobot 4")) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 4",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ))
                }
                Button(
                    onClick = { navController.navigate(
                        Screens.SocketsT02_7_Screen.route.replace(
                            "{machine_name}", "Telerobot 5")) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 5",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ))
                }
                Button(
                    onClick = { navController.navigate(
                        Screens.SocketsT02_7_Screen.route.replace(
                            "{machine_name}", "Telerobot 6")) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 6",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ))
                }
                Button(
                    onClick = { navController.navigate(
                        Screens.SocketsT02_7_Screen.route.replace(
                            "{machine_name}", "Telerobot 7")) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 20.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray),
                ) {
                    Text(text = "Telerobot 7",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onSurface

                        ))
                }
            }
        }
    }
}
