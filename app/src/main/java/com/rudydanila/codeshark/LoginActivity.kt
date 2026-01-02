package com.rudydanila.codeshark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.rudydanila.codeshark.ui.theme.CodeSharkTheme
import androidx.credentials.GetCredentialRequest
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webClientId = "113583699572-crfodb6gqvjfntqm0h3q2br4sc4u1jpq.apps.googleusercontent.com"

        setContent {
            CodeSharkTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(
                        webClientId = webClientId,
                        onLoginClick = {
                            // TODO: обработка обычного входа (email/пароль)
                        },
                        onGoogleClick = { request: GetCredentialRequest ->
                            lifecycleScope.launch {
                                signIn(request, this@LoginActivity)
                            }
                        }
                    )
                }
            }
        }
    }
}
