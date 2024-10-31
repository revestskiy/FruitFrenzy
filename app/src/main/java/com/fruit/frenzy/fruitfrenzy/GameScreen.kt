package com.fruit.frenzy.fruitfrenzy

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fruit.frenzy.fruitfrenzy.ui.theme.nujnoefont
import kotlinx.coroutines.delay
import kotlin.math.sqrt
import kotlin.random.Random

typealias All = Any?

infix fun All.not(any: All) = this != any

// Define a function to calculate time remaining based on level
fun calculateTimeForLevel(level: Int): Int {
    val baseTime = 120 // Base time in seconds for the first level
    val timeDecreasePerLevel = 2 // Decrease time by 2 seconds per level
    val minimumTime = 30 // Minimum time limit in seconds

    // Calculate the time remaining for the current level
    val calculatedTime = baseTime - (timeDecreasePerLevel * (level - 1))
    return maxOf(calculatedTime, minimumTime) // Ensure it doesn't go below minimumTime
}

@Composable
fun GameScreen(
    level: Int,
    onHome: () -> Unit,
    onBack: () -> Unit,
    onNextOrRetry: (Int) -> Unit
) {
    var score by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }
    var elements by remember { mutableStateOf(listOf<GameElement>()) }
    var timeRemaining by remember { mutableIntStateOf(calculateTimeForLevel(level)) } // 90 seconds per level
    var isGameRunning by remember { mutableStateOf(true) }
    var isSettingsVisible by remember { mutableStateOf(false) }
    val initGoals = remember { getGoalsForLevel(level) }
    var goals by remember { mutableStateOf(initGoals) }
    var scoreEffects by remember { mutableStateOf(listOf<Pair<Int, GameElement>>()) } // Store score effects
    var explodingElements by remember { mutableStateOf(listOf<GameElement>()) }
    var isWinner by remember { mutableStateOf(false) }
    // Timer logic
    LaunchedEffect(isGameRunning) {
        if (isGameRunning) {
            while (timeRemaining > 0 && isGameRunning) {
                delay(1000L)
                if (isSettingsVisible not true) {
                    timeRemaining--
                }
            }
            if (timeRemaining == 0) {
                isGameRunning = false
                isGameOver = true
                if (score < level * 100) {
                    isWinner = false
                }
                else {
                    isWinner = score >= level * 100 && goals.all { it.value == 0 }
                    if (isWinner && level < MAX_LEVEL) {
                        if (Prefs.level < level + 1) Prefs.level =
                            level + 1 // Unlock the next level if not at max level
                    }
                }
            }
        }
    }
    LaunchedEffect(score) {
        if (score >= level * 100 && goals.all { it.value == 0 }) {
            isWinner = true
            if (Prefs.level < level + 1) Prefs.level = level + 1
            isGameOver = true
        }
    }
    when {
        isSettingsVisible -> {
            BackHandler {
                isSettingsVisible = false
            }
            OptionsScreen { isSettingsVisible = false }
        }

        isGameOver -> {
            ResultScreen(
                isWinner = isWinner,
                level = level,
                score = score,
                goals = goals.mapValues {
                    Pair(it.value, initGoals[it.key]!!)
                },
                onNextOrRetry = {
                    onNextOrRetry(it)
                }) {
                onHome()
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = Prefs.selectedBg),
                            contentScale = ContentScale.Crop
                        )
                        .background(Color.Black.copy(alpha = 0.5f))

                        .padding(16.dp)
                ) {
                    // Top Bar: Level, Score, Timer, Settings
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.backbutton),
                            contentDescription = "Back Button",
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { onBack() }
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("LEVEL $level", fontSize = 18.sp, color = Color.White)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(6) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Color.White, CircleShape)
                                    )
                                }
                            }
                            Text("SCORE: $score", fontSize = 14.sp, color = Color.White)
                            Text(
                                "TIME: ${timeRemaining.secondsToTimeString}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.settingsbutton),
                            contentDescription = "Settings Button",
                            modifier = Modifier
                                .size(60.dp)
                                .clickable { isSettingsVisible = true }
                        )
                    }

                    // Game Elements
                    BoxWithConstraints(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(
                                top = 100.dp,
                            )
                    ) {
                        val topBarHeight = LocalDensity.current.run { 100.dp.toPx() }
                        LaunchedEffect(isGameRunning, level) {
                            if (isGameRunning) {
                                while (isGameOver not true) {
                                    val newElements = createRandomElements(
                                        level,
                                        maxHeight - topBarHeight.dp,
                                        maxWidth
                                    )
                                    elements = elements + newElements

                                    val spawnDelay = when (level) {
                                        1 -> 2000L
                                        2 -> 1800
                                        3 -> 1600
                                        4 -> 1400
                                        5 -> 1200L
                                        else -> 1200L
                                    }
                                    delay(spawnDelay + 1500) // Faster spawning on higher levels

                                    elements = elements.filter { it !in newElements }
                                }
                            }
                        }

                        elements.forEach { element ->
                            ElementItem(
                                element = element,
                                onElementClick = { clickedElement ->
                                    runCatching {
                                        val scoreChange = if (clickedElement.isBomb) -50 else 10
                                        if (clickedElement.isBomb) {
                                            explodingElements = explodingElements + clickedElement
                                        }
                                        score += scoreChange
                                        when (clickedElement.type) {
                                            "strawberry" -> {
                                                if ((goals["strawberry"] ?: -1) > 0) {
                                                    val mutableGoals = goals.toMutableMap()
                                                    mutableGoals["strawberry"] =
                                                        mutableGoals["strawberry"]!! - 1
                                                    goals = mutableGoals
                                                }
                                            }

                                            "leaf" -> {
                                                if ((goals["leaf"] ?: -1) > 0) {
                                                    val mutableGoals = goals.toMutableMap()
                                                    mutableGoals["leaf"] =
                                                        mutableGoals["leaf"]!! - 1
                                                    goals = mutableGoals
                                                }
                                            }

                                            "grape" -> {
                                                if ((goals["grape"] ?: -1) > 0) {
                                                    val mutableGoals = goals.toMutableMap()
                                                    mutableGoals["grape"] =
                                                        mutableGoals["grape"]!! - 1
                                                    goals = mutableGoals
                                                }
                                            }

                                            "cherry" -> {
                                                if ((goals["cherry"] ?: -1) > 0) {
                                                    val mutableGoals = goals.toMutableMap()
                                                    mutableGoals["cherry"] =
                                                        mutableGoals["cherry"]!! - 1
                                                    goals = mutableGoals
                                                }
                                            }
                                        }
                                        if (score < 0) {
                                            score = 0
                                        }
                                        scoreEffects =
                                            scoreEffects + (scoreChange to clickedElement)
                                        elements = elements.filter { it != clickedElement }
                                    }
                                }
                            )
                        }
                        explodingElements.forEach { explodingElement ->
                            ExplodeEffect(explodingElement.positionX, explodingElement.positionY) {
                                explodingElements = explodingElements - explodingElement
                            }
                        }
                        scoreEffects.forEach { (scoreChange, element) ->
                            ScoreEffect(
                                scoreChange = scoreChange,
                                x = element.positionX,
                                y = element.positionY,
                                onAnimationEnd = {
                                    scoreEffects =
                                        scoreEffects.filter { it != scoreChange to element }
                                }
                            )
                        }
                    }

                    // Bottom Goals Display
                }
                Row(
                    modifier = Modifier
                        .paint(
                            painterResource(id = R.drawable.itembg),
                            contentScale = ContentScale.Crop
                        )
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    goals.forEach { (type, count) ->
                        GoalItem(type = type, count = count)
                    }
                }
            }
        }
    }
}

@Composable
fun GoalItem(type: String, count: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = drawableForType(type)),
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = "$count",
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ScoreEffect(
    scoreChange: Int,
    x: Float,
    y: Float,
    onAnimationEnd: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(true) }
    val animatedScale by animateFloatAsState(if (visible) 1.2f else 0.0f) // scale effect
    val animatedAlpha by animateFloatAsState(if (visible) 1.0f else 0.0f) // fade effect

    LaunchedEffect(Unit) {
        delay(500L) // Show the score change for 0.5 seconds
        visible = false
        delay(300L) // Wait for fade out
        onAnimationEnd() // Notify the animation is done
    }

    Box(
        modifier = Modifier
            .offset(x.dp, y.dp)
            .scale(animatedScale)
            .alpha(animatedAlpha),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (scoreChange > 0) "+$scoreChange" else "$scoreChange",
            fontFamily = nujnoefont,
            fontSize = 24.sp,
            color = Color.White,
        )
    }
}


@Composable
fun ExplodeEffect(x: Float, y: Float, onExploded: () -> Unit = {}) {
    // Simple explosion effect using scale animation
    var exploded by remember { mutableStateOf(false) }
    val explosionScale by animateFloatAsState(if (exploded) 1f else 0.5f)

    LaunchedEffect(Unit) {
        exploded = true
        delay(500L)
        exploded = false
        delay(200L)
        onExploded()
    }

    Box(
        modifier = Modifier
            .offset(x.dp, y.dp)
            .scale(explosionScale)// You can replace this with an explosion drawable resource
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_explosion), // Placeholder explosion resource
            contentDescription = "Explosion",
            modifier = Modifier.size(100.dp)
        )
    }
}

fun drawableForType(type: String): Int {
    return when (type) {
        "strawberry" -> R.drawable.ic_strawberry
        "leaf" -> R.drawable.ic_lime
        "grape" -> R.drawable.ic_grape
        "cherry" -> R.drawable.ic_cherry
        else -> R.drawable.ic_strawberry
    }
}

@Composable
fun ElementItem(element: GameElement, onElementClick: (GameElement) -> Unit) {
    Box(
        modifier = Modifier
            .offset(element.positionX.dp, element.positionY.dp)
            .size(50.dp)
            .clickable { onElementClick(element) }
    ) {
        Image(
            painter = painterResource(id = element.drawableId),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    }
}

data class GameElement(
    val id: Int,
    val drawableId: Int,
    val isBomb: Boolean,
    val type: String, // e.g., "strawberry", "leaf", etc.
    val positionX: Float,
    val positionY: Float
)

val Int.secondsToTimeString: String
    get() {
        val minutes = this / 60
        val seconds = this % 60
        return String.format("%02d:%02d", minutes, seconds)
    }


fun createRandomElements(level: Int, maxHeight: Dp, maxWidth: Dp): List<GameElement> {
    val numberOfElements = Random.nextInt(10 + level, 20 + level)
    val minDistance = 100f
    val maxAttempts = 10

    val safeAreaStartX = 0f
    val safeAreaEndX = maxWidth.value
    val safeAreaStartY = 0f
    val safeAreaEndY = maxHeight.value

    val newElements = mutableListOf<GameElement>()

    repeat(numberOfElements) {
        var positionX: Float
        var positionY: Float
        var validPositionFound: Boolean
        var attempts = 0

        do {
            validPositionFound = true
            positionX = Random.nextFloat() * (safeAreaEndX - safeAreaStartX) + safeAreaStartX
            positionY = Random.nextFloat() * (safeAreaEndY - safeAreaStartY) + safeAreaStartY

            for (element in newElements) {
                val distance =
                    calculateDistance(positionX, positionY, element.positionX, element.positionY)
                if (distance < minDistance) {
                    validPositionFound = false
                    attempts++
                    break
                }
            }
        } while (!validPositionFound && attempts < maxAttempts)

        if (validPositionFound) {
            val isBomb = randomBoolean(truePossibility = 0.2f)
            val id = Random.nextInt(1000)
            val random = Random.nextInt(5)
            val drawableId = if (isBomb) {
                R.drawable.ic_bomb
            } else {
                when (random) {
                    0 -> R.drawable.ic_strawberry
                    1 -> R.drawable.ic_lime
                    2 -> R.drawable.ic_grape
                    3 -> R.drawable.ic_cherry
                    else -> R.drawable.ic_strawberry
                }
            }

            newElements.add(
                GameElement(
                    id = id,
                    drawableId = drawableId,
                    isBomb = isBomb,
                    positionX = positionX,
                    positionY = positionY,
                    type = if (isBomb) "bomb" else when (random) {
                        0 -> "strawberry"
                        1 -> "leaf"
                        2 -> "grape"
                        3 -> "cherry"
                        else -> "strawberry"
                    }
                )
            )
        }
    }
    return newElements
}


fun randomBoolean(truePossibility: Float): Boolean {
    return Random.nextFloat() < truePossibility
}

// Helper function to calculate distance between two points
fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
    return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
}