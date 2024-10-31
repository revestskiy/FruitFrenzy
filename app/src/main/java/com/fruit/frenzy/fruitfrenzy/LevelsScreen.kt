package com.fruit.frenzy.fruitfrenzy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fruit.frenzy.fruitfrenzy.ui.theme.nujnoefont
import kotlinx.coroutines.launch

const val MAX_LEVEL = 48

// A function to generate goals based on the level
fun getGoalsForLevel(level: Int): Map<String, Int> {
    return when (level) {
        in 1..10 -> mapOf("strawberry" to 5 + level, "leaf" to 7 + level, "grape" to 8 + level)
        in 11..20 -> mapOf("strawberry" to 10 + level, "leaf" to 12 + level, "grape" to 13 + level)
        in 21..30 -> mapOf("strawberry" to 15 + level, "leaf" to 17 + level, "grape" to 18 + level)
        in 31..40 -> mapOf("strawberry" to 20 + level, "leaf" to 22 + level, "grape" to 23 + level)
        else -> mapOf("strawberry" to 25 + level, "leaf" to 27 + level, "grape" to 28 + level)
    }
}

@Composable
fun LevelsScreen(
    onLevelSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState { 4 }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = Prefs.selectedBg), // Заменить на ваш фон
                contentScale = ContentScale.Crop
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.backbutton), // Заменить на ваш ресурс
            contentDescription = "Back Button",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopStart)
                .padding(16.dp)
                .clickable {
                    onBack()
                }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.levelsbutton), // Укажите ваш ресурс
                contentDescription = "Get Button",
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .size(170.dp, 70.dp)
            )
            HorizontalPager(state = pagerState) {
                LevelsPage(it * 12)  { level ->
                    onLevelSelected(level)
                }
            }
            HorizontalIndicator(
                pagerState = pagerState
            )
        }
    }
}

@Composable
fun HorizontalIndicator(pagerState: androidx.compose.foundation.pager.PagerState) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .background(
                Color(0xff9AA299),
                RoundedCornerShape(10)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(4) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        if (pagerState.currentPage == it) Color(0xff904406) else Color.White,
                        CircleShape
                    ).clickable {
                        scope.launch {
                            pagerState.scrollToPage(it)
                        }
                    }
            )
        }
    }
}

@Composable
fun LevelsPage(startLevel: Int, onLevel: (Int) -> Unit = {}) {
    Column {
        for (row in 0 until 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0 until 3) {
                    val level = startLevel + row * 3 + col + 1
                    LevelButton(level) {
                        onLevel(level)
                    }
                }
            }
        }
    }
}

@Composable
fun LevelButton(level: Int, onClick: (Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .alpha(
                if (Prefs.level >= level) 1f else 0.5f
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_rect),
            contentDescription = "Level Button",
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .clickable {
                    if (Prefs.level >= level) {
                        onClick(level)
                    }
                }
        )
        Text(
            text = "$level",
            modifier = Modifier.align(Alignment.Center),
            fontFamily = nujnoefont,
            color = Color(0xff562904),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}