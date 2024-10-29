package com.fruit.frenzy.fruitfrenzy

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fruit.frenzy.fruitfrenzy.ui.theme.nujnoefont

@Preview
@Composable
fun BonusScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background), // Укажите ваш фон
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

                        }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Кнопка "EXIT" чуть выше
            Image(
                painter = painterResource(id = R.drawable.bonusbutton), // Укажите ваш ресурс
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
                        painterResource(id = R.drawable.backgroundbonus),
                        contentScale = ContentScale.Fit
                    ) // Фон деревяшки
                    .padding(16.dp)
                    .size(420.dp, 400.dp)
            ) {
            }
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.getbutton), // Укажите ваш ресурс
                contentDescription = "Get Button",
                modifier = Modifier
                    .size(140.dp, 50.dp)
                    .clickable {
                        // Обработчик нажатия кнопки "GET"
                    }
            )
        }
    }
}