package ru.europlast.europlasttech

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.europlast.europlasttech.data.CurrentTask
import ru.europlast.europlasttech.data.NetworkInterface
import ru.europlast.europlasttech.ui.theme.EvpCyan
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtn
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtnBorder

class CurrentTasksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}

@Composable
fun CurrentTasksScreen(navController: NavController, networkAPI: NetworkInterface) {
    val tasksList = remember { mutableStateOf<List<CurrentTask>>(emptyList()) }
    var isInfoVisible by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        updateTaskList(tasksList, networkAPI)
    }
//    val tasksList = listOf(
//        CurrentTask(
//            id = UUID.randomUUID(),
//            task = "TO-3 | Telerobot 1",
//            workers = listOf("Worker 1", "Worker 2"),
//            status = "В работе",
//            techcard = "TO-3",
//            pathtophoto = "path",
//            createtime = "01-01-01 20:01:10.99338",
//            author = "Me",
//            spentrepairparts = listOf("Zaxvat L", "Zaxvat R")
//
//        ), CurrentTask(
//            id = UUID.randomUUID(),
//            task = "TO-2 | Telerobot 2",
//            workers = listOf("Worker 1", "Worker 2"),
//            status = "В работе",
//            techcard = "TO-2",
//            pathtophoto = "path",
//            createtime = "01-01-01 20:01:10.99338",
//            author = "Me",
//            spentrepairparts = listOf("Zaxvat L", "Zaxvat R")
//
//        ))

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_img),
            contentDescription = "background image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "return Icon",
                tint = Color.Black,
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable {
                        navController.navigate(Screens.MainScreen.route) {
                            popUpTo("current_tasks_screen") { inclusive = true }
                        }
                    }
                    .zIndex(1f),

                )
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "update tasks Icon",
                tint = Color.Green,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable { updateTaskList(tasksList, networkAPI) }
                    .zIndex(1f),
                )
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Task status info",
                tint = Color.Black,
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable { isInfoVisible = true }
                    .zIndex(1f),
                )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 15.dp, end = 20.dp, start = 20.dp)
                .zIndex(2f),
        ) {
            itemsIndexed(tasksList.value) { _, item ->
                CurrentTaskCard(task = item, networkAPI, navController)
            }
        }
    }
    if (isInfoVisible) { InfoDialog(onDismiss = {isInfoVisible = false}) }
}

fun updateTaskList(tasksList: MutableState<List<CurrentTask>>, networkAPI: NetworkInterface){
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = networkAPI.getCurrentTasks()
            if (response.isSuccessful) {
                tasksList.value = response.body() ?: emptyList()
            } else {
                Log.e("CurrentTasksScreen", "Error: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.e("CurrentTasksScreen", "Error updating task list: ${e.message}")
        }
    }
}
@Composable
fun CurrentTaskCard(task: CurrentTask,
                    networkAPI: NetworkInterface,
                    navController: NavController) {
    val textColor by remember { mutableStateOf(Color.Black) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val statusColor = when (task.status) {
        "В работе" -> Color.Yellow
        "Выполнена" -> Color.Green
        "Отменена" -> Color.Gray
        "Создана" -> Color.Red
        else -> {
            MaterialTheme.colorScheme.primary
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable(onClick = { isDialogVisible = !isDialogVisible }),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(color = statusColor)
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = task.task,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
    val context = LocalContext.current
    var isToastVisible by remember { mutableStateOf(false) }

    if (isDialogVisible) {
        if("ТО" in task.task && task.status == "В работе"){ /* ТО на русском */
            LaunchedEffect(Unit) {
                try{
                    val response = task.techcard?.let { networkAPI.getTechCard(it) }
                    if (response?.body() == null){
                        isToastVisible = true
                    }
                    else{
                        Log.d("[CurrentTasksRunTECHCARD]",
                            "Navigating to CheckListScreen with techCard: ${task.techcard}")
                        navController.navigate("checklist_screen/${task.techcard}")
                    }
                }
                catch (e: Exception){
                    Log.d("CheckListScreen|LaunchedEffect", "Error: ${e.message}")
                }
            }

        } else {
            FullCardInfo(task, onDismiss = { isDialogVisible = false }, networkAPI)
        }
    }
    if (isToastVisible){
        Toast.makeText(context, "Техническая карта не найдена", Toast.LENGTH_LONG).show()
        isToastVisible = false
    }
}


@Composable
fun FullCardInfo(choisenCard: CurrentTask, onDismiss: () -> Unit, networkAPI: NetworkInterface) {
    var changeStatusCode by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf(choisenCard.status) }
    val context = LocalContext.current
    val successChangeStatus = stringResource(R.string.success_change_status)


    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close Dialog Icon",
                    tint = colorResource(android.R.color.darker_gray),
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { onDismiss() }
                        .zIndex(1f)
                        .align(Alignment.TopEnd),
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_save_24),
                    contentDescription = "Save Icon",
                    tint = colorResource(android.R.color.darker_gray),
                    modifier = Modifier
                        .padding(top = 15.dp, start = 15.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable {
                            onUpdateStatusClick(
                                choisenCard, networkAPI,
                                statusText, context, successChangeStatus
                            )
                            onDismiss()
                        }
                        .zIndex(1f)
                        .align(Alignment.TopStart),
                )
            }
            Column(modifier = Modifier.padding(20.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(top = 30.dp),
                        text = choisenCard.task,
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )

                }
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://avatars.mds.yandex.net/i?id=0aef40c429ff11a449a8329ef6de247211b6b28a-12512933-images-thumbs&n=13"
                    ),
                    contentDescription = "Task picture",
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Fit,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Cyan, shape = RoundedCornerShape(9.dp))
                        .border(2.dp, color = EvpCyan, shape = RoundedCornerShape(9.dp))
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.workers),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                    )
                }
                choisenCard.workers.forEachIndexed() { index, worker ->
                    Text(
                        text = "${index + 1}. $worker",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Cyan, shape = RoundedCornerShape(9.dp))
                        .border(2.dp, color = EvpCyan, shape = RoundedCornerShape(9.dp))
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.status),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                    )
                }
                Text(
                    text = statusText,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Cyan, shape = RoundedCornerShape(9.dp))
                        .border(2.dp, color = EvpCyan, shape = RoundedCornerShape(9.dp))
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.task_date),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(horizontal = 15.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    text = choisenCard.createtime,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Cyan, shape = RoundedCornerShape(9.dp))
                        .border(2.dp, color = EvpCyan, shape = RoundedCornerShape(9.dp))
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = stringResource(R.string.author),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                    )
                }
                Text(
                    text = choisenCard.author,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Button(
                    onClick = { changeStatusCode = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .zIndex(1f)
                        .border(
                            2.dp, color = SaveAndExitBtnBorder,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(SaveAndExitBtn)
                ) {
                    Text(
                        text = stringResource(R.string.change_status_code),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )

                }
                if (changeStatusCode) {
                    ChangeStatus(onDismiss = { changeStatusCode = false },
                        onStatusSelected = { status ->
                            statusText = status
                            changeStatusCode = false
                        })
                }
            }

        }
    }
}


fun onUpdateStatusClick(
    choisenCard: CurrentTask, networkAPI: NetworkInterface,
    statusText: String, context: Context, successChangeStatus: String
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = networkAPI.updateTaskStatus(
                taskId = choisenCard.id, status = statusText
            )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context, successChangeStatus,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Log.e("TaskScreen", "Error: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.e("TaskScreen", "Error updating task status: ${e.message}")
        }
    }
}


@Composable
fun ChangeStatus(onDismiss: () -> Unit, onStatusSelected: (String) -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close Dialog",
                    tint = colorResource(android.R.color.darker_gray),
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { onDismiss() }
                        .zIndex(1f)
                        .align(Alignment.TopEnd),
                )
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.padding(20.dp))
                    Row() {
                        Button(
                            onClick = { onStatusSelected("В работе") },
                            modifier = Modifier
                                .shadow(5.dp, shape = RoundedCornerShape(30.dp))
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth()
                                .zIndex(1f),
                            colors = ButtonDefaults.buttonColors(Color.Yellow)
                        ) {
                            Text(
                                text = "В работе",
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )

                        }
                    }
                    Row(modifier = Modifier.padding(vertical = 10.dp)) {
                        Button(
                            onClick = { onStatusSelected("Отменена") },
                            modifier = Modifier
                                .shadow(5.dp, shape = RoundedCornerShape(30.dp))
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth()
                                .zIndex(1f),
                            colors = ButtonDefaults.buttonColors(Color.Gray)
                        ) {
                            Text(
                                text = "Отменена",
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )

                        }
                    }
                    Row() {
                        Button(
                            onClick = { onStatusSelected("Выполнено") },
                            modifier = Modifier
                                .shadow(5.dp, shape = RoundedCornerShape(30.dp))
                                .padding(horizontal = 4.dp)
                                .fillMaxWidth()
                                .zIndex(1f),
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(
                                text = "Выполнено",
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoDialog(onDismiss: () -> Unit){

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close Dialog",
                    tint = colorResource(android.R.color.darker_gray),
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { onDismiss() }
                        .zIndex(1f)
                        .align(Alignment.TopEnd),
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 60.dp, horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(120.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically){
                        Box(modifier = Modifier
                            .shadow(10.dp, shape = RoundedCornerShape(25.dp))
                            .width(50.dp)
                            .height(50.dp)
                            .background(Color.Yellow)
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = stringResource(R.string.in_progress_status),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically){
                        Box(modifier = Modifier
                            .shadow(10.dp, shape = RoundedCornerShape(25.dp))
                            .width(50.dp)
                            .height(50.dp)
                            .background(Color.Red)
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = stringResource(R.string.created_status),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        )
                    }


                }
            }
        }

    }
}

