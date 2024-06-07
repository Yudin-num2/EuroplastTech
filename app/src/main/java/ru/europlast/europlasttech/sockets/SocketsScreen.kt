package ru.europlast.europlasttech.sockets

import android.util.Log
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.europlast.europlasttech.ui.theme.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.europlast.europlasttech.R
import ru.europlast.europlasttech.data.ButtonColorData
import ru.europlast.europlasttech.data.ButtonColorsList

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SocketScreen(
    machineName: String,
    list: List<String>,
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: SocketViewModel = viewModel()
    val buttonColors by viewModel.buttonColors.collectAsState()
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    val parseColors = mapOf(
    "goodSocket" to Color(0xFF37FF00),
    "Underfilling" to Color(0xFF1C71D8),
    "Dirt" to Color(0xFFAC7E64),
    "NeedleChipping" to Color(0xFF7641D1),
    "FlashByClosure" to Color(0xFFA0A71B),
    "Crack" to Color(0xFFFF29EC),
    "Hole" to Color(0xFFE01B24),
    "GateValve" to Color(0xFFC0FF41),
    "NotWarm" to Color(0xFF003287),
    "Geometry" to Color(0xFFD2691E),
    "Water" to Color(0xFF00DAC7)
    )
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
        Text(
            text = machineName,
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Black,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 55.dp)
        )
        Button(
            onClick = {
                val replacedButtonColors = buttonColors.mapValues { (_, innerMap) ->
                    innerMap.mapKeys { (key, _) ->
                        key + 1
                    }.mapValues { (_, color) ->
                        parseColors.entries.find { it.value == color }?.key ?: color.toString()
                    }
                }
                Log.d("RESULT_COLORS", replacedButtonColors.toString())
                      },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
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
                .height(55.dp)
                .zIndex(3f),
            selectedTabIndex = pagerState.currentPage,
            indicator = indicator,
            containerColor = EvpCyan,
        ) {
            list.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .zIndex(6f),
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
                .zIndex(1f),
            count = list.size,
            state = pagerState,
        ) { page ->
            Box(
                contentAlignment = Alignment.Center
            ) {
                when (page) {
                    0 -> LidSockets48()
                    1 -> FrameSockets48()
                    2 -> CutterSockets48()
                    3 -> LidSockets12()
                    4 -> FrameSockets12()
                    5 -> CutterSockets12()
                }

            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage)
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
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
@Preview
fun PreviewSocketsScreen() {
    SocketScreen(machineName = "Telerobot 2", list = listOf("Lid", "Frame", "Cutter"))
}


//fun saveBtnParser(buttonCol: Map<String, Map<Int, Color>>): String {
//    val buttonColorDataList = mutableListOf<ButtonColorData>()
//
//    buttonCol.forEach { (page, colorsMap) ->
//        colorsMap.forEach { (buttonIndex, color) ->
//        }
//    }
//}