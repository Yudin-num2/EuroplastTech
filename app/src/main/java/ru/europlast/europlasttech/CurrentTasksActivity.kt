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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import ru.europlast.europlasttech.data.CurrentTask
import ru.europlast.europlasttech.data.CurrentTasksList

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
fun CurrentTasksScreen(navController: NavController, tasksList: CurrentTasksList) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 5.dp, end = 20.dp, start = 20.dp,)
                .zIndex(1f),
        ) {
            itemsIndexed(tasksList.currentTasks.toList()) { _, item ->
                CurrentTaskCard(task = item)
            }
        }
    }
}

@Composable
//@Preview
fun CurrentTaskCard(
    task: CurrentTask
     ) {
//    val task = CurrentTask()//*********************
    val textColor by remember { mutableStateOf(Color.Black) }
    var openFullCardInfo by remember{ mutableStateOf(false)}
    val statusColor = when (task.status) {
        stringResource(R.string.in_progress_status) -> Color.Yellow
        stringResource(R.string.completed_status) -> Color.Green
        stringResource(R.string.canceled_status) -> Color.Gray
        stringResource(R.string.created_status) -> Color.Red
        else -> {MaterialTheme.colorScheme.primary}
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp)
        .clickable(onClick = { openFullCardInfo = !openFullCardInfo }),
        elevation = CardDefaults.cardElevation(5.dp),) {
        Row(modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier = Modifier
                .height(30.dp)
                .width(30.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = statusColor)){}
            Text(modifier = Modifier.padding(horizontal = 10.dp),
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
    if (openFullCardInfo) {
        FullCardInfo()
    }
}

@Composable
@Preview
fun FullCardInfo(
    //onDismiss() -> Unit
){
    val choisenCard = CurrentTask(
        task = "ЗадачаЗадачаЗадачаЗадачаЗадача",
        workers = listOf("Worker1", "Worker2"),
        status = "Создана",
        techCard = "",
        pathToPhoto = "/home/nonanon/coding/Kotlin/EuroplastTech/Images/notFoundPhoto.jpeg",
        createTime = "01-01-01 15:16:17.99999",
        author = "Сысоев Илья",
        spentRepairParts = listOf("Part1", "Part2")
    )
    var isDialogVisible by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = { isDialogVisible = false },
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
                        .padding(top = 15.dp, end = 15.dp,)
                        .width(30.dp)
                        .height(30.dp)
                        .clickable {
                            isDialogVisible = false
                            Log.d("Dialog", "Нажата иконка")
                        }
                        .zIndex(1f)
                        .align(Alignment.TopEnd),
                )
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 30.dp),
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
                            model = "https://avatars.mds.yandex.net/i?id=0aef40c429ff11a449a8329ef6de247211b6b28a-12512933-images-thumbs&n=13"),
                        contentDescription = "Task picture",
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Fit,
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(3.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                        .padding(3.dp),
                        contentAlignment = Alignment.Center){

                        Text(text = stringResource(id = R.string.workers),
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,)
                        )
                    }
                    choisenCard.workers.forEachIndexed() {index, worker ->
                        Text(text = "${index + 1}. $worker",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Black),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(3.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                        .padding(3.dp),
                        contentAlignment = Alignment.Center){

                        Text(text = stringResource(id = R.string.status),
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,)
                        )
                    }
                        Text(text = choisenCard.status,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Black),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(3.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                        .padding(3.dp),
                        contentAlignment = Alignment.Center){

                        Text(text = when(choisenCard.status) {
                            stringResource(R.string.in_progress_status) ->
                                stringResource(R.string.in_progress_date)
                            stringResource(R.string.completed_status) ->
                                stringResource(R.string.completed_date)
                            stringResource(R.string.canceled_status) ->
                                stringResource(R.string.canceled_date)
                            stringResource(R.string.created_status) ->
                                stringResource(R.string.created_date)
                            else -> {stringResource(R.string.unknown)}}, ///?????
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,)
                        )
                    }
                        Text(text = choisenCard.createTime,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Black),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(3.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                        .padding(3.dp),
                        contentAlignment = Alignment.Center){

                        Text(text = stringResource(id = R.string.author),
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,)
                        )
                    }
                        Text(text = choisenCard.author,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Black),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                    //TODO Button change status
                }
            }
        }
    }
}

