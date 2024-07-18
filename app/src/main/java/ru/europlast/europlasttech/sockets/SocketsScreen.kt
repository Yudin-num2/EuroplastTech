package ru.europlast.europlasttech.sockets

import android.util.Log
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.europlast.europlasttech.R
import ru.europlast.europlasttech.Screens
import ru.europlast.europlasttech.data.RetrofitInstance.networkAPI
import ru.europlast.europlasttech.ui.theme.Black
import ru.europlast.europlasttech.ui.theme.EvpCyan
import ru.europlast.europlasttech.ui.theme.SaveAndExitBtn
import ru.europlast.europlasttech.ui.theme.SocketTabIndicatorBG
import ru.europlast.europlasttech.ui.theme.SocketTabIndicatorBorder


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SocketScreen(
    machineName: String,
    list: List<String>? = null,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: SocketViewModel = viewModel(
        factory = SocketViewModelFactory(machineName)
    )
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    val gson = Gson()
    var isInfoDialogVisible by remember{ mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_background_img),
            contentDescription = "background_img",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 60.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "return Icon",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable {
                        navController.navigate(Screens.MainScreen.route) {
                            popUpTo("current_tasks_screen") { inclusive = true }
                        }
                    }
                    .zIndex(10f),
            )
            Text(
                text = machineName,
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Task status info",
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 15.dp)
                    .width(30.dp)
                    .height(30.dp)
                    .clickable { isInfoDialogVisible = true }
                    .zIndex(10f),
            )
        }

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    val result = gson.toJson(viewModel.buttonColors.value)
                    networkAPI.updateSockets(
                        machineName, result
                    )
                }
                navController.navigate(Screens.MainScreen.route) {
                    popUpTo("current_tasks_screen") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                .align(Alignment.BottomEnd)
                .zIndex(3f),
            colors = ButtonDefaults.buttonColors(SaveAndExitBtn),
        ) {
            Text(
                text = stringResource(R.string.save_and_exit), style = TextStyle(
                    textAlign = TextAlign.Center, fontSize = 24.sp, color = Black,
                    fontWeight = FontWeight.Bold,
                )
            )
        }

        ScrollableTabRow(
            modifier = Modifier
                .height(56.dp)
                .zIndex(2f),
            selectedTabIndex = pagerState.currentPage,
            indicator = indicator,
            containerColor = EvpCyan,
            divider = {},
        ) {
            list?.forEachIndexed { index, title ->
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
        if (list != null) {
            HorizontalPager(
                modifier = Modifier
                    .zIndex(1f)
                    .padding(top = 100.dp),
                count = list.size,
                state = pagerState,
            ) { page ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    if (machineName == "Telerobot 3") {
                        when (page) {
                            0 -> Sockets64("Lid")
                            1 -> Sockets64("Frame")
                            2 -> Sockets64("Cutter")
                        }
                    } else {
                        when (page) {
                            0 -> Sockets48("Lid")
                            1 -> Sockets48("Frame")
                            2 -> Sockets48("Cutter")
                            3 -> Sockets12("Lid12")
                            4 -> Sockets12("Frame12")
                            5 -> Sockets12("Cutter12")
                        }
                    }
                }
            }
        } else {
            //TODO глянь количество гнёзд на всех машинах (ручка отличается от других)
        }
    }
        if(isInfoDialogVisible){ InfoDialog(onDismiss = {isInfoDialogVisible = false}) }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 500f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 500f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.CenterStart)
            .width(indicatorEnd - indicatorStart)
            .padding(2.dp)
            .fillMaxSize()
            .background(color = SocketTabIndicatorBG, RoundedCornerShape(50))
            .border(BorderStroke(2.dp, SocketTabIndicatorBorder), RoundedCornerShape(50))
            .zIndex(1f)

    )
}


@Composable
fun InfoDialog(onDismiss: () -> Unit) {
    val listOfReasons = mutableMapOf(
        stringResource(R.string.good_socket) to Color(0xFF37FF00),
        stringResource(R.string.underfilling) to Color(0xFF1C71D8),
        stringResource(R.string.dirt) to Color(0xFFAC7E64),
        stringResource(R.string.needle_chipping) to Color(0xFF7641D1),
        stringResource(R.string.flash_by_closure) to Color(0xFFA0A71B),
        stringResource(R.string.crack) to Color(0xFFFF29EC),
        stringResource(R.string.hole) to Color(0xFFE01B24),
        stringResource(R.string.gate_valve) to Color(0xFFC0FF41),
        stringResource(R.string.not_warm) to Color(0xFF003287),
        stringResource(R.string.geometry) to Color(0xFFD2691E),
        stringResource(R.string.water) to Color(0xFF00DAC7),
    )


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
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    for ((name, color) in listOfReasons) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .shadow(15.dp, shape = RoundedCornerShape(25.dp))
                                    .width(50.dp)
                                    .height(50.dp)
                                    .background(color)
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                text = name,
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
}
//@Composable
//@Preview
//fun PreviewSocketsScreen() {
//    SocketScreen(machineName = "Telerobot 2", list = listOf("Lid", "Frame", "Cutter"))
//}

