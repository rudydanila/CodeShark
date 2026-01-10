package com.rudydanila.codeshark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.regex.Pattern

@Composable
fun ExerciseScreen1(onBackClick: () -> Unit) {
    val initialCode = """
        #include <_>
        using _ std;
        int _() {
            cout << "Hello World!";
            _ 0;
        }
    """.trimIndent()

    // Эталон для сравнения (без пробелов)
    val correctFinalCode = """
        #include <iostream>
        using namespace std;
        int main() {
            cout << "Hello World!";
            return 0;
        }
    """.trimIndent().replace(Regex("\\s+"), "")

    val options = listOf("iostream", "namespace", "main", "return")
    var currentCode by remember { mutableStateOf(initialCode) }
    var usedOptions by remember { mutableStateOf(setOf<String>()) }
    var resultState by remember { mutableStateOf<Boolean?>(null) }

    fun checkAnswers() {
        // Убираем все пробелы и переносы для честного сравнения
        val userCodeCleaned = currentCode.replace(Regex("\\s+"), "")

        if (currentCode.contains("_")) {
            resultState = false
            return
        }

        resultState = userCodeCleaned == correctFinalCode
    }

    fun insertOption(option: String) {
        if (currentCode.contains("_")) {
            currentCode = currentCode.replaceFirst("_", option)
            usedOptions = usedOptions + option
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1F1B2D))) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White) }
            Text("Задание 1", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        CodeSnippetCard(lines = currentCode.split("\n"))

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                if (!usedOptions.contains(option)) {
                    OptionButton(text = option, onClick = { insertOption(option) })
                }
            }
        }

        CheckAnswerButton(resultState) {
            if (resultState == true) onBackClick()
            else checkAnswers()
        }
    }
}

@Composable
fun ExerciseScreen2(onBackClick: () -> Unit) {
    val initialCode = """
        #include <_>
        #include <cstdlib>
        using _ std;
        int main()
        {
           double x = 0;
           cout << "x = ";
           cin >> x;
           cout << "x*x";
           if (x*x < 2)
              cout << "<";
           else
              cout << ">";
           cout << "2" << _ << __;
           return EXIT_SUCCESS;
        }
    """.trimIndent()

    val options = listOf("iostream", "namespace", "endl")
    val correctAnswersInOrder = listOf("iostream", "namespace", "endl")

    var currentCode by remember { mutableStateOf(initialCode) }
    var usedOptions by remember { mutableStateOf(setOf<String>()) }
    var resultState by remember { mutableStateOf<Boolean?>(null) }

    fun checkAnswers() {
        if (currentCode.contains("_") || currentCode.contains("__")) {
            resultState = false
            return
        }
        val insertedAnswers = currentCode.split(Pattern.compile("[\\s;<>(),]+")).filter { it in options }
        resultState = insertedAnswers == correctAnswersInOrder
    }

    fun insertOption(option: String) {
        if (currentCode.contains("_")) {
            currentCode = currentCode.replaceFirst("_", option)
            usedOptions = usedOptions + option
        } else if (currentCode.contains("__")) {
            currentCode = currentCode.replaceFirst("__", option)
            usedOptions = usedOptions + option
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1F1B2D))) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White) }
            Text("Задание 2", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp)) {
            CodeSnippetCard(lines = currentCode.split("\n"))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { option ->
                    if (!usedOptions.contains(option)) {
                        OptionButtonSmall(text = option, onClick = { insertOption(option) })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            CheckAnswerButton(resultState) {
                if (resultState == true) onBackClick()
                else checkAnswers()
            }
        }
    }
}
@Composable
fun ExerciseScreen3(onBackClick: () -> Unit) {
    val initialCode = listOf(
        "1. #include <iostream>",
        "2. using namespace std;",
        "3. int main() {",
        "4.   int _, _;",
        "5.   _ = 5;",
        "6.   y = 10;",
        "7.   cout << _ << endl;",
        "8.   return 0;",
        "9. }"
    ).joinToString("\n")

    val options = listOf("x", "y", "x", "y")
    val correctAnswersInOrder = listOf("x", "y", "x", "y")

    var currentCode by remember { mutableStateOf(initialCode) }
    var usedOptionsCount by remember { mutableStateOf(0) }
    var resultState by remember { mutableStateOf<Boolean?>(null) }

    fun checkAnswers() {
        if (currentCode.contains("_")) {
            resultState = false
            return
        }
        val insertedAnswers = currentCode.split(Pattern.compile("[\\s,;]+")).filter { it == "x" || it == "y" }
        resultState = insertedAnswers == correctAnswersInOrder
    }

    fun insertOption(option: String) {
        if (currentCode.contains("_")) {
            currentCode = currentCode.replaceFirst("_", option)
            usedOptionsCount++
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1F1B2D))) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White) }
            Text("Задание 3", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        CodeSnippetCard(lines = currentCode.split("\n"))

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("x", "y").forEach { option ->
                OptionButtonSmall(text = option, onClick = { insertOption(option) })
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CheckAnswerButton(resultState) {
            if (resultState == true) onBackClick()
            else checkAnswers()
        }
    }
}
@Composable
fun ExerciseScreen4(onBackClick: () -> Unit) {
    val initialCode = """
        #include <iostream>
        using namespace std;
        int main() {
            int a = 10, b = 20;
            if (a _ b) {
                cout << "a is less";
            } _ {
                cout << "b is less or equal";
            }
            _ 0;
        }
    """.trimIndent()

    val options = listOf("<", "else", "return")
    val correctAnswersInOrder = listOf("<", "else", "return")

    var currentCode by remember { mutableStateOf(initialCode) }
    var usedOptions by remember { mutableStateOf(setOf<String>()) }
    var resultState by remember { mutableStateOf<Boolean?>(null) }

    fun checkAnswers() {
        if (currentCode.contains("_")) {
            resultState = false
            return
        }
        val parts = currentCode.split(Pattern.compile("[\\s()]+"))
        val inserted = options.filter { it in parts }
        resultState = inserted == correctAnswersInOrder
    }

    fun insertOption(option: String) {
        if (currentCode.contains("_")) {
            currentCode = currentCode.replaceFirst("_", option)
            usedOptions = usedOptions + option
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1F1B2D))) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White) }
            Text("Задание 4", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        CodeSnippetCard(lines = currentCode.split("\n"))

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            options.forEach { option ->
                if (option !in usedOptions) {
                    OptionButton(text = option, onClick = { insertOption(option) })
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CheckAnswerButton(resultState) {
            if (resultState == true) onBackClick()
            else checkAnswers()
        }
    }
}



@Composable
fun CodeSnippetCard(lines: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D2D2D)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            lines.forEach { line ->
                HighlightingText(line = line)
            }
        }
    }
}

@Composable
fun HighlightingText(line: String) {
    val defaultColor = Color.White.copy(alpha = 0.8f)
    val answerColor = Color(0xFF87CEEB)
    val placeholderColor = Color.Gray

    Text(
        buildAnnotatedString {
            // Разделяем строку по заполнителям '_' и '__'
            line.split(Pattern.compile("(_+)")).forEach { segment ->
                if (segment.startsWith("_")) {
                    // Это оставшийся заполнитель
                    withStyle(style = SpanStyle(color = placeholderColor)) {
                        append(segment)
                    }
                } else {
                    // Это либо статический код, либо вставленный ответ
                    withStyle(style = SpanStyle(color = defaultColor)) {
                        append(segment)
                    }
                }
            }
        },
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun OptionButton(text: String, onClick: () -> Unit) { /* Код OptionButton */
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
        elevation = null,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Text(text, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(8.dp))
    }
}
@Composable
fun OptionButtonSmall(text: String, onClick: () -> Unit) { /* Код OptionButtonSmall */
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}
@Composable
fun CheckAnswerButton(resultState: Boolean?, onClick: () -> Unit) {
    val buttonColor = when (resultState) {
        true -> Color(0xFF32CD32)
        false -> Color(0xFFDC143C)
        else -> Color(0xFF6A5ACD)
    }
    val buttonText = if (resultState == true) "Продолжить" else "Проверить"

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
    ) {
        Text(buttonText, color = Color.White, fontSize = 16.sp)
    }
}
