package com.fruit.frenzy.fruitfrenzy

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen(
    onBack: () -> Unit,
) {

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
                painter = painterResource(id = R.drawable.infobutton), // Укажите ваш ресурс
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(200.dp, 80.dp)
            )

            // Деревянный фон для кнопок
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .paint(
                        painterResource(id = R.drawable.backgroundinfo),
                        contentScale = ContentScale.Fit
                    ) // Фон деревяшки
                    .padding(16.dp)
                    .size(550.dp, 480.dp)
            ) {

            }
        }
    }
}