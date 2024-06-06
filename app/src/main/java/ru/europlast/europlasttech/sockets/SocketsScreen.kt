package ru.europlast.europlasttech.sockets

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.europlast.europlasttech.ui.theme.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.*
import ru.europlast.europlasttech.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SocketScreen(
    machineName: String,
    list: List<String>,
) {
    val pagerState = rememberPagerState()
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 30.dp)) {

        ScrollableTabRow(
            modifier = Modifier
                .height(55.dp),
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
                    onClick = { },
                )
            }
        }
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            count = list.size,
            state = pagerState,
        ) { page ->
            Box(Modifier
                .fillMaxSize(),) {
                Image(
                    painter = painterResource(id = R.drawable.ic_background_img),
                    contentDescription = "background_img",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp)
                )
                when (page) {
                    0 -> LidSockets48()
                    1 -> FrameSockets48()
                    2 -> CutterSockets48()
                    3 -> LidSockets12()
                    4 -> FrameSockets12()
                    5 -> CutterSockets12()
                }
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
fun previewSocketsScreen() {
    SocketScreen(machineName = "Telerobot 2", list = listOf("Lid", "Frame", "Cutter"))
}