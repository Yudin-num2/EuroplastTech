package ru.europlast.europlasttech

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
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
    Box(modifier = Modifier.fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp, bottom = 5.dp, end = 20.dp, start = 20.dp, )
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

    val statusColor = when (task.status) {
        "В работе" -> Color.Yellow
        "Завершено" -> Color.Green
        "Отменено" -> Color.Gray
        "Создана" -> Color.Red
        else -> {MaterialTheme.colorScheme.primary}
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp)
        .clickable(onClick = { Log.d("CurrentTaskCard", "onClick" + task.task) })) {
        Row(modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clip(shape = RoundedCornerShape(50.dp))
                .background(color = statusColor)){}
            Text(modifier = Modifier.padding(horizontal = 10.dp),
                text = task.task,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                )
        }
    }
}
