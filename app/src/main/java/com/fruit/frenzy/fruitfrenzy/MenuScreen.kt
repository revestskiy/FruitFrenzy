package com.fruit.frenzy.fruitfrenzy

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun MenuScreen(

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.startbutton),
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {

                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.options),
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {

                    }
            )

            Spacer(modifier = Modifier.height(12.dp))



            Image(
                painter = painterResource(id = R.drawable.bonusbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.height(12.dp))


            Image(
                painter = painterResource(id = R.drawable.infobutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {

                    }
            )
            Spacer(modifier = Modifier.height(12.dp))


            Image(
                painter = painterResource(id = R.drawable.exitbutton),
                contentDescription = "Exit Button",
                modifier = Modifier
                    .size(width = 230.dp, height = 100.dp)
                    .clickable {

                    }
            )
        }
    }
}