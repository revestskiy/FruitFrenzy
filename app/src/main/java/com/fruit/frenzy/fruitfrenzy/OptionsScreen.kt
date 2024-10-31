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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.fruit.frenzy.fruitfrenzy.ui.theme.nujnoefont

@Composable
fun OptionsScreen(
    onBack: () -> Unit,
) {
    val previews = remember {
        listOf(
            R.drawable.bg_preview1,
            R.drawable.bg_preview2,
            R.drawable.bg_preview3,
            R.drawable.bg_preview4
        )
    }
    var soundEnabled by remember { mutableStateOf(Prefs.soundVolume > 0) }
    var musicEnabled by remember { mutableStateOf(Prefs.musicVolume > 0) }
    var selectedPreview by remember {
        mutableIntStateOf(
            when (Prefs.selectedBg) {
                R.drawable.background -> 0
                R.drawable.bg2 -> 1
                R.drawable.bg3 -> 2
                R.drawable.bg4 -> 3
                else -> 0
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = Prefs.selectedBg), // Replace with your background
                contentScale = ContentScale.Crop
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.backbutton), // Replace with your back button
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
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .paint(
                        painterResource(id = R.drawable.bg_wood), // Replace with your background
                        contentScale = ContentScale.Crop
                    )
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.options), // Replace with your options button
                    contentDescription = "Options",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .size(170.dp, 70.dp)
                )
                SettingsOption(
                    label = "SOUND",
                    isEnabled = soundEnabled,
                    onToggle = { soundEnabled = it }
                )
                SettingsOption(
                    label = "MUSIC",
                    isEnabled = musicEnabled,
                    onToggle = { musicEnabled = it },
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            Image(
                painter = painterResource(id = previews[selectedPreview]), // Replace with your background preview
                contentDescription = "Background Preview",
                modifier = Modifier
                    .size(300.dp)
                    .padding(16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.bg_wood), // Replace with your background
                        contentScale = ContentScale.Crop
                    )
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        selectedPreview = (selectedPreview - 1 + previews.size) % previews.size
                    },
                    modifier = Modifier
                        .size(58.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.left_arrow), // Replace with your left arrow
                        contentDescription = "Previous Background",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                            .alpha(
                                if (selectedPreview == 0) 0.5f else 1f
                            )
                    )
                }
                Text(
                    text = "BACKGROUND",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontFamily = nujnoefont
                )
                IconButton(
                    onClick = {
                        selectedPreview = (selectedPreview + 1) % previews.size

                    },
                    modifier = Modifier
                        .size(58.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.right_arrow), // Replace with your right arrow
                        contentDescription = "Next Background",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                            .alpha(
                                if (selectedPreview == previews.size - 1) 0.5f else 1f
                            )
                    )
                }
            }
            Button(
                onClick = {
                    Prefs.selectedBg = when (selectedPreview) {
                        0 -> R.drawable.background
                        1 -> R.drawable.bg2
                        2 -> R.drawable.bg3
                        3 -> R.drawable.bg4
                        else -> 0
                    }

                    Prefs.soundVolume = if (soundEnabled) 0.5f else 0f
                    Prefs.musicVolume = if (musicEnabled) 0.5f else 0f
                    SoundManager.setSoundVolume()
                    SoundManager.setMusicVolume()
                    onBack()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffa4d643) // Adjust as needed for button color
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .padding(bottom = 16.dp)
                    .size(170.dp, 70.dp)
            ) {
                Text(
                    text = "SAVE",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = nujnoefont
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun SettingsOption(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            color = Color.White,
            fontFamily = nujnoefont
        )
        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xffa4d643),
                uncheckedThumbColor = Color.Gray
            )
        )
    }
}
