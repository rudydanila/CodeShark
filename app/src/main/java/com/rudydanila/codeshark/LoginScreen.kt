package com.rudydanila.codeshark

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.ImageOptions
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    context: Context,
    onLoginSuccess: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box {
            CoilImage(
                imageModel = { "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/vqXW9WdZlC/gmtxuce3_expires_30_days.png" },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                modifier = Modifier.fillMaxSize()
            )

            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xB0665793), Color(0xFF1F1B2D)),
                                start = Offset.Zero,
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        )
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    // Поле ввода почты
                    Text("Введите почту",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 21.dp, bottom = 12.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )

                    // Поле ввода пароля
                    Text("Введите пароль",
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 21.dp, bottom = 12.dp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(bottom = 11.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Забыли пароль?",
                            color = Color(0xFFB5B3F1),
                            fontSize = 14.sp,
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                try {
                                    val fakeEmail = if (email.isNotEmpty()) email else "user@example.com"
                                    Toast.makeText(context, "Вход выполнен: $fakeEmail", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess(fakeEmail)
                                } catch (ex: Exception) {
                                    errorMessage = ex.message
                                }
                            }
                        },
                        border = BorderStroke(0.dp, Color.Transparent),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFD9D9D9)
                        ),
                        contentPadding = PaddingValues(),
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxWidth()
                            .background(Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
                    ) {
                        Text("Войти", color = Color.White, fontSize = 14.sp)
                    }
                    errorMessage?.let {
                        Text(it, color = Color.Red, modifier = Modifier.padding(16.dp))
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Нет аккаунта? Зарегистрируйтесь",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
