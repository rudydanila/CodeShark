package com.rudydanila.codeshark

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KnowledgeLevelScreen(onLevelChosen: (String) -> Unit) {
    val backgroundColor = Color(0xFF1F1B2D)
    val cardColor = Color(0xFF2D2942)
    val accentColor = Color(0xFF7C4DFF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(backgroundColor, Color(0xFF121019))
                )
            )
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ваш уровень 🎯",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Выберите сложность обучения",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        LevelCard(
            title = "Новичок",
            subtitle = "Основы синтаксиса и циклы",
            icon = Icons.Default.School,
            color = Color(0xFF4CAF50),
            onClick = { onLevelChosen("beginner") }
        )

        LevelCard(
            title = "Средний",
            subtitle = "Массивы, функции и указатели",
            icon = Icons.Default.ElectricBolt,
            color = Color(0xFFFFC107),
            onClick = { onLevelChosen("intermediate") }
        )

        LevelCard(
            title = "Продвинутый",
            subtitle = "ООП, шаблоны и структуры",
            icon = Icons.Default.Star,
            color = Color(0xFFF44336),
            onClick = { onLevelChosen("advanced") }
        )
    }
}

@Composable
fun LevelCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF2D2942),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color)
            }

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
