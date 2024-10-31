package com.fruit.frenzy.fruitfrenzy

import android.app.Activity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fruit.frenzy.fruitfrenzy.ui.theme.nujnoefont


@Composable
fun ExitScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current as Activity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = Prefs.selectedBg), // Укажите ваш фон
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // Кнопка "Назад" в левом верхнем углу
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backbutton), // Укажите ваш ресурс
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.TopStart)
                        .clickable {
                            onBack()
                        }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Кнопка "EXIT" чуть выше
            Image(
                painter = painterResource(id = R.drawable.exitbutton), // Укажите ваш ресурс
                contentDescription = "Exit Button",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(200.dp, 80.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Деревянный фон для кнопок
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .paint(painterResource(id = R.drawable.backgroundexit), contentScale = ContentScale.Fit) // Фон деревяшки
                    .padding(16.dp)
                    .size(500.dp, 200.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Кнопка "YES"
                    Button(
                        onClick = { context.finishAndRemoveTask() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3CC825)
                        ),
                        modifier = Modifier.size(80.dp, 40.dp)
                    ) {
                        Text(
                            text = "YES",
                            fontFamily = nujnoefont, // Укажите ваш шрифт
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Кнопка "NO"
                    Button(
                        onClick = { onBack() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff9AA299)
                        ),
                        modifier = Modifier.size(80.dp, 40.dp)
                    ) {
                        Text(
                            text = "NO",
                            fontFamily = nujnoefont, // Укажите ваш шрифт
                            color = Color(0xff626060)
                        )
                    }
                }
            }
        }
    }
}
