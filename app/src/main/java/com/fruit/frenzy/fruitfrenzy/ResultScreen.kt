package com.fruit.frenzy.fruitfrenzy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fruit.frenzy.fruitfrenzy.ui.theme.nujnoefont

@Composable
fun ResultScreen(
    isWinner: Boolean,
    level: Int,
    score: Int,
    goals: Map<String, Pair<Int, Int>>, // Map of goal type and Pair(current, required)
    onNextOrRetry: (Int) -> Unit,
    onHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = Prefs.selectedBg), // Replace with your background resource
                contentScale = ContentScale.Crop
            )
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isWinner) "WINNER" else "LOSE",
                fontFamily = nujnoefont,
                fontSize = 36.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (isWinner) {
                Text(
                    text = "LEVEL $level",
                    fontFamily = nujnoefont,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = "SCORE: $score",
                    fontFamily = nujnoefont,
                    fontSize = 20.sp,
                    color = Color.White
                )
            } else {
                Text(
                    text = "GOALS NOT ACHIEVED",
                    fontFamily = nujnoefont,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Goals Display
            Box(
                modifier = Modifier
                    .background(Color(0x80000000), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    goals.forEach { (type, progress) ->
                        GoalItem(type = type, count = progress.first, required = progress.second)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = if (isWinner) R.drawable.nextbutton else R.drawable.retrybutton),
                    contentDescription = if (isWinner) "Next" else "Retry",
                    modifier = Modifier
                        .size(170.dp, 70.dp)
                        .clickable { onNextOrRetry(if (isWinner) level + 1 else level) }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Image(
                    painter = painterResource(id = R.drawable.homebutton),
                    contentDescription = "Home",
                    modifier = Modifier
                        .size(170.dp, 70.dp)
                        .clickable { onHome() }
                )
            }
        }
    }
}

@Composable
fun GoalItem(type: String, count: Int, required: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = drawableForType(type)),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "$count/$required",
            color = Color.White,
            fontSize = 15.sp
        )
    }
}
