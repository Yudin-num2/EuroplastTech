package ru.europlast.europlasttech

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
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
                    onClick = { navController.navigate(Screens.AddDefectScreen.route) },
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
                    onClick = { navController.navigate(Screens.AddAnomalyScreen.route) },
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
    onDismiss:() -> Unit,
    navController: NavController,
) {
    val machines = mutableListOf(
        "Telerobot 1", "Telerobot 2", "Telerobot 3",
        "Telerobot 4", "Telerobot 5", "Telerobot 6",
        "Telerobot 7", "Husky 11", "Husky 12",
         "Husky 16", "Husky 22", "HeliCap",
    )
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
                .padding(10.dp)
                .border(1.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
                .height(530.dp),
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

                LazyColumn {
                    items(machines){machineName ->
                        Button(
                            onClick = {
                                if(machineName == "Telerobot 1"){

                                    navController.navigate(Screens.SocketsT01Screen.route)
                                }else if(machineName.contains("Husky")){
                                    navController.navigate(Screens.SocketsHusky.route)
                                }else{ navController.navigate(
                                Screens.SocketsT02_7_Screen.route.replace(
                                    "{machine_name}", machineName)) }},
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(bottom = 20.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(3.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(Color.Gray),
                        ) {
                            Text(text = machineName,
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
    }
}
