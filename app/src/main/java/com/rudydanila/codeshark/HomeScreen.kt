package com.rudydanila.codeshark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudydanila.codeshark.data.Level
import kotlinx.coroutines.launch

val initialLevels = listOf(
    Level(1, 1, 1, isCompleted = true),
    Level(2, 1, 2, isCompleted = true),
    Level(3, 1, 3, isCompleted = false),
    Level(4, 1, 4, isCompleted = false),

    Level(5, 2, 1, isCompleted = true),
    Level(6, 2, 2, isCompleted = true),
    Level(7, 2, 3, isCompleted = false),
    Level(8, 2, 4, isCompleted = false),

    Level(5, 3, 1, isCompleted = false),
    Level(6, 3, 2, isCompleted = false),
    Level(7, 3, 3, isCompleted = false),
    Level(8, 3, 4, isCompleted = false),

    Level(5, 4, 1, isCompleted = false),
    Level(6, 4, 2, isCompleted = false),
    Level(7, 4, 3, isCompleted = false),
    Level(8, 4, 4, isCompleted = false),

    Level(1, 5, 1, isCompleted = true),
    Level(2, 5, 2, isCompleted = true),
    Level(3, 5, 3, isCompleted = false),
    Level(4, 5, 4, isCompleted = false),

    Level(5, 6, 1, isCompleted = true),
    Level(6, 6, 2, isCompleted = true),
    Level(7, 6, 3, isCompleted = false),
    Level(8, 6, 4, isCompleted = false),

    Level(5, 7, 1, isCompleted = false),
    Level(6, 7, 2, isCompleted = false),
    Level(7, 7, 3, isCompleted = false),
    Level(8, 7, 4, isCompleted = false),

    Level(5, 8, 1, isCompleted = false),
    Level(6, 8, 2, isCompleted = false),
    Level(7, 8, 3, isCompleted = false),
    Level(8, 8, 4, isCompleted = false),
)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    onBackClick: () -> Unit,
    onLevelClick: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val levels = remember { mutableStateListOf(*initialLevels.toTypedArray()) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { DrawerContent(onSettingsClick = {}, onProfileClick = {}, onAchievementsClick = {}) }
        },
        content = {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    NativeGradientBackground()

                    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                        HomeHeader(
                            onBackClick = onBackClick,
                            onProfileClick = { scope.launch { drawerState.open() } }
                        )

                        LevelSelectorGrid(levels, onLevelClick)
                    }
                }
            }
        }
    )
}

@Composable
fun NativeGradientBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A5ACD), Color(0xFF1F1B2D)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    )
}

@Composable
fun LevelSelectorGrid(levels: List<Level>, onLevelClick: (Int) -> Unit) {
    val groupedLevels = levels.groupBy { it.chapter }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        groupedLevels.forEach { (chapter, chapterLevels) ->
            val chapterName = when(chapter) {
                1 -> "Глава 1: Линейные алгоритмы"
                2 -> "Глава 2: Циклы"
                3 -> "Глава 3: Массивы"
                4 -> "Глава 4: Функции"
                5 -> "Глава 5: Указатели"
                6 -> "Глава 6: ООП"
                7 -> "Глава 7: Структуры"
                8 -> "Глава 8: Шаблоны"
                else -> "Глава $chapter"
            }
            ChapterButton(chapterName, onClick = { /* TODO: Открыть обзор главы */ })
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                chapterLevels.forEachIndexed { index, level ->
                    val isPreviousCompleted = if (index == 0) { true } else { chapterLevels[index - 1].isCompleted }
                    val isEnabled = level.isCompleted || isPreviousCompleted

                    NumberedLevelButton(
                        number = level.taskNumber,
                        isCompleted = level.isCompleted,
                        isEnabled = isEnabled,
                        onClick = {
                            if (isEnabled) {
                                onLevelClick(level.id)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun NumberedLevelButton(number: Int, isCompleted: Boolean, isEnabled: Boolean, onClick: () -> Unit = {}) {
    val buttonColor = when {
        isCompleted -> Color(0xFF32CD32)
        isEnabled -> Color(0x66FFFFFF)
        else -> Color(0x1AFFFFFF)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(buttonColor, CircleShape)
            .border(BorderStroke(2.dp, Color.White.copy(alpha = 0.3f)), CircleShape)
            .clickable(enabled = isEnabled, onClick = onClick)
    ) {
        if (isCompleted) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Пройдено",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        } else {
            Text(
                text = number.toString(),
                color = if (isEnabled) Color.White else Color.Gray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DrawerContent(onSettingsClick: () -> Unit, onProfileClick: () -> Unit, onAchievementsClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text("Меню", style = MaterialTheme.typography.headlineLarge, color = Color.Black)
        Spacer(modifier = Modifier.height(24.dp))
        DrawerItem("Профиль", Icons.Default.Info, onProfileClick)
        Divider()
        DrawerItem("Достижения", Icons.Default.Info, onAchievementsClick)
        Divider()
        DrawerItem("Настройки", Icons.Default.Settings, onSettingsClick)
        Divider()
        DrawerItem("Выход", Icons.Default.ExitToApp, {})
    }
}

@Composable
fun DrawerItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.DarkGray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = Color.Black)
    }
}

@Composable
fun HomeHeader(onBackClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 56.dp, start = 21.dp, end = 21.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Меню",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0x80D9D9D9))
                .clickable { onProfileClick() }
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Профиль",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Профиль пользователя",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Composable
fun ChapterButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick, // Просто вызываем колбэк!
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0x33D9D9D9)),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Справка",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
