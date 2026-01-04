package com.rudydanila.codeshark

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KnowledgeLevelScreen(onLevelChosen: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Выбор уровня знаний 🎯",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { onLevelChosen("beginner") }, modifier = Modifier.fillMaxWidth()) {
            Text("Новичок")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onLevelChosen("intermediate") }, modifier = Modifier.fillMaxWidth()) {
            Text("Средний")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onLevelChosen("advanced") }, modifier = Modifier.fillMaxWidth()) {
            Text("Продвинутый")
        }
    }
}
