package ru.europlast.europlasttech

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import ru.europlast.europlasttech.data.NetworkInterface
import ru.europlast.europlasttech.data.TechCard
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtn
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtnBorder


class CheckListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}

@Composable
fun CheckListScreen(
    navController: NavController,
    networkAPI: NetworkInterface,
    techCard: String?) {
    var operations by remember { mutableStateOf<TechCard?>(null) }

    LaunchedEffect(Unit) {
        try{
            val response = techCard?.let { networkAPI.getTechCard(it) }
            operations = response?.body()
        }
        catch (e: Exception){
            Log.d("CheckListScreen|LaunchedEffect", "Error: ${e.message}")
        }
    }
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
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "return Icon",
                tint = Color.Black,
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .zIndex(1f)
                    .clickable {
                        navController.navigate(Screens.CurrentTasksScreen.route) {
                            popUpTo("check_list_screen") { inclusive = true }
                        }
                    }
                )
            Text(
                text = techCard!!,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 80.dp, end = 15.dp)){
            Text(
                modifier = Modifier.weight(3f),
                text = "Операция",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )
            Text(
                modifier = Modifier.weight(2f),
                text = "Примечание",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            )

        }
        val operationList = mutableListOf<String>()
        operations?.telerobot?.forEach { operationList.add("Сборка | $it") }
        operations?.pressform?.forEach { operationList.add("Прессформа | $it") }
        operations?.tpa?.forEach { operationList.add("ТПА | $it") }
        operations?.greenBox?.forEach { operationList.add("GreenBox | $it") }
        operations?.otk?.forEach { operationList.add("ОТК | $it") }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp, bottom = 80.dp)
                .zIndex(3f),
        ) {
            items(operationList.size) { index ->
                OneElementRow(text = operationList[index])
            }
        }
        Button(
            onClick = { /*TODO get json and push on DB*/
                navController.navigate(Screens.CurrentTasksScreen.route){
                popUpTo("check_list_screen") { inclusive = true } }
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp, start = 15.dp, end = 15.dp)
                .height(50.dp)
                .zIndex(2f)
                .align(Alignment.BottomCenter)
                .border(
                    2.dp, color = SaveAndExitBtnBorder,
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = ButtonDefaults.buttonColors(SaveAndExitBtn)
        ) {
            Text(modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.save_and_exit),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )

        }

    }
}

@Composable
fun OneElementRow(text: String) {
    var statusCheckbox by remember{ mutableStateOf(false) }
    var comment by remember{ mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .defaultMinSize(minHeight = 30.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start
            ),
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        Checkbox(
            modifier = Modifier.weight(1f),
            checked = statusCheckbox,
            onCheckedChange = {statusCheckbox = !statusCheckbox})
        TextField(
            value = comment,
            onValueChange = { comment = it },
            textStyle = TextStyle(color = Color.Black),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 30.dp)
                .padding(vertical = 5.dp)
                .weight(2f)
        )
    }
}
