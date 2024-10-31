package com.fruit.frenzy.fruitfrenzy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@Composable
fun MenuScreen(
    onStart: () -> Unit,
    onOptions: () -> Unit,
    onBonus: () -> Unit,
    onInfo: () -> Unit,
    onExit: () -> Unit
) {
    var timeRemaining by remember { mutableLongStateOf(getTimeUntilNextBonus()) }
    var isBonusAvailable by remember { mutableStateOf(Prefs.isBonusAvailable()) }

    // Update the timer every second
    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            delay(1000L) // Delay for 1 second
            timeRemaining = getTimeUntilNextBonus()
            isBonusAvailable = Prefs.isBonusAvailable()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = Prefs.selectedBg),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.present_counter),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .alpha(if (isBonusAvailable) 1f else 0.5f)
                    .clickable(enabled = isBonusAvailable) {
                        onBonus()
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Timer Text: Display time remaining until the next bonus if not available
            if (timeRemaining > 0) {
                Text(
                    text = formatTime(timeRemaining),
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color(0xffFEBD3F),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            } else {
                Text(
                    text = "Bonus available!",
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color(0xffFEBD3F),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.startbutton),
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {
                        onStart()
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.options),
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {
                        onOptions()
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))



            Image(
                painter = painterResource(id = R.drawable.bonusbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .alpha(
                        if (isBonusAvailable) 1f else 0.5f
                    )
                    .clickable(enabled = isBonusAvailable) {
                        onBonus()
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))


            Image(
                painter = painterResource(id = R.drawable.infobutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {
                        onInfo()
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))


            Image(
                painter = painterResource(id = R.drawable.exitbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {
                        onExit()
                    }
            )
        }
    }
}

fun getTimeUntilNextBonus(): Long {
    val currentTime = System.currentTimeMillis()
    val fiveHoursInMillis = 5 * 60 * 60 * 1000L
    val lastBonusTime = Prefs.lastBonusTimestamp
    return (lastBonusTime + fiveHoursInMillis) - currentTime
}

// Helper function to format milliseconds into HH:mm:ss format
fun formatTime(timeInMillis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeInMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}