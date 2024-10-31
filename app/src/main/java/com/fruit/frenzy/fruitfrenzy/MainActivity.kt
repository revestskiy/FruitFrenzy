package com.fruit.frenzy.fruitfrenzy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fruit.frenzy.fruitfrenzy.ui.theme.FruitFrenzyTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Prefs.init(application)
        SoundManager.init(application)
        setContent {
            val navController = rememberNavController()
            FruitFrenzyTheme {
                NavHost(navController = navController, startDestination = "loading") {
                    composable("loading") {
                        LoadingScreen {
                            navController.navigate("home") {
                                popUpTo("loading") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable("exit") {
                        ExitScreen(
                            onBack = {
                                navController.popBackStack()
                                SoundManager.playSound()
                            }
                        )
                    }
                    composable("options") {
                        OptionsScreen(
                            onBack = {
                                navController.popBackStack()
                                SoundManager.playSound()
                            }
                        )
                    }
                    composable("bonus") {
                        BonusScreen(
                            onBack = {
                                navController.popBackStack()
                                SoundManager.playSound()

                            }
                        )
                    }
                    composable("game") {
                        LevelsScreen(
                            onLevelSelected = {
                                navController.navigate("game/$it")
                                SoundManager.playSound()
                            },
                            onBack = {
                                navController.popBackStack()
                                SoundManager.playSound()

                            }
                        )
                    }
                    composable("home") {
                        MenuScreen(
                            onExit = {
                                navController.navigate("exit")
                                SoundManager.playSound()
                            },
                            onStart = {
                                navController.navigate("game")
                                SoundManager.playSound()
                            },
                            onOptions = {
                                navController.navigate("options")
                                SoundManager.playSound()
                            },
                            onInfo = {
                                navController.navigate("info")
                                SoundManager.playSound()
                            },
                            onBonus = {
                                navController.navigate("bonus")
                                SoundManager.playSound()
                            }
                        )
                    }
                    composable("info") {
                        InfoScreen(
                            onBack = {
                                navController.popBackStack()
                                SoundManager.playSound()
                            }
                        )
                    }
                    composable("game/{level}") {
                        val level = it.arguments?.getString("level")?.toInt() ?: 1
                        GameScreen(
                            onBack = {
                                navController.popBackStack()
                                SoundManager.playSound()
                            },
                            level = level,
                            onHome = {
                                navController.navigate("home") {
                                    popUpTo("game/$level") {
                                        inclusive = true
                                    }
                                }
                                SoundManager.playSound()
                            },
                            onNextOrRetry = { lvl ->
                                navController.navigate("game/$lvl") {
                                    popUpTo("game/$lvl") {
                                        inclusive = true
                                    }
                                }
                                SoundManager.playSound()
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        SoundManager.resumeMusic()
    }

    override fun onPause() {
        super.onPause()
        SoundManager.pauseMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.onDestroy()
    }

}

@Composable
fun LoadingScreen(
    onNext: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNext()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.loading),
                contentScale = ContentScale.Crop
            )
    )
}
