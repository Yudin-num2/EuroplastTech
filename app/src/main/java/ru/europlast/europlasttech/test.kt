package ru.europlast.europlasttech

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ru.europlast.europlasttech.data.ChecklistItem
import ru.europlast.europlasttech.data.RetrofitInstance.networkAPI
import ru.europlast.europlasttech.data.TechCard
import ru.europlast.europlasttech.sockets.CustomIndicator
import ru.europlast.europlasttech.sockets.SocketViewModel
import ru.europlast.europlasttech.sockets.SocketViewModelFactory
import ru.europlast.europlasttech.ui.theme.EvpCyan
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtn
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtnBorder

@OptIn(ExperimentalPagerApi::class)
@Composable
//@Preview
fun CheckListScr() {
    val viewModel: OperationsViewModel = viewModel(
        factory = OperationsViewModelFactory(techCard = "ТО-2")
    )
    val operations by viewModel.operations.collectAsState()
    val list = listOf("Telerobot", "Lid", "Frame", "Cutter")
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    val techCard = "ТО-2"
    val gson = Gson()
    val isLoading by viewModel.isLoading.collectAsState()



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
        ScrollableTabRow(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp)
                .height(56.dp)
                .zIndex(2f),
            selectedTabIndex = pagerState.currentPage,
            indicator = indicator,
            containerColor = EvpCyan,
            divider = {},
        ) {
            list.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .zIndex(2f),
                    text = { Text(text = title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .zIndex(1f)
                .padding(top = 80.dp),
            count = list.size,
            state = pagerState,
        ) { page ->
            Box(
                contentAlignment = Alignment.Center,

            ) {
//                val operationList = when (page) {
//                    0 -> operations.telerobot
//                    else -> (operations.tpa.orEmpty() + operations.greenBox.orEmpty()
//                            + operations.pressform.orEmpty()).distinct()
//                }

                when (page) {
                    0 -> if(isLoading){
                        CircularProgressIndicator()
                    }else{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 120.dp)
                        ) {
                            LazyColumn {
                                val operats = viewModel.getOperationsForMachine("Telerobot")
                                items(operats){
                                        operationName -> OneElement(text = operationName)
                                }
                            }
                        }
                    }

                    1 -> if(isLoading){
                        CircularProgressIndicator()
                    }else{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 120.dp)
                        ) {
                            LazyColumn {
                                val operats = viewModel.getOperationsForMachine("Lid")
                                items(operats){
                                        operationName -> OneElement(text = operationName)
                                }
                            }
                        }
                    }
                    2 -> if(isLoading){
                        CircularProgressIndicator()
                    }else{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 120.dp)
                        ) {
                            LazyColumn {
                                val operats = viewModel.getOperationsForMachine("Frame")
                                items(operats){
                                        operationName -> OneElement(text = operationName)
                                }
                            }
                        }
                    }

                    3 -> if(isLoading){
                        CircularProgressIndicator()
                    }else{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 120.dp)
                        ) {
                            LazyColumn {
                                val operats = viewModel.getOperationsForMachine("Cutter")
                                items(operats){
                                        operationName -> OneElement(text = operationName)
                                }
                            }
                        }
                    }


                }
                Button(
                    onClick = { /*TODO get json and push on DB*/
                        /*navController.navigate(Screens.CurrentTasksScreen.route) {
                            popUpTo("check_list_screen") { inclusive = true }
                        }*/
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 60.dp, start = 15.dp, end = 15.dp)
                        .height(50.dp)
                        .zIndex(2f)
                        .border(
                            2.dp, color = SaveAndExitBtnBorder,
                            shape = RoundedCornerShape(20.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(SaveAndExitBtn)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
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
    }
}

@Composable
fun Operat(machineName: String, viewModel: OperationsViewModel){

}

@Composable
fun OneElement(text: String) {
    var statusCheckbox by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }
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
                modifier = Modifier.padding(5.dp)
            )
        }
        Checkbox(
            modifier = Modifier.weight(1f),
            checked = statusCheckbox,
            onCheckedChange = { statusCheckbox = !statusCheckbox })
        TextField(
            value = comment,
            onValueChange = { comment = it },
            textStyle = TextStyle(color = Color.Black),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 30.dp)
                .padding(5.dp)
                .weight(2f)
        )
    }
}

