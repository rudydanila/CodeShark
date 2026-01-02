package com.rudydanila.codeshark

import java.security.SecureRandom
import java.util.Base64
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.delay
fun generateSecureRandomNonce(byteLength: Int = 32): String {
    val randomBytes = ByteArray(byteLength)
    SecureRandom.getInstanceStrong().nextBytes(randomBytes)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
}


suspend fun signIn(request: GetCredentialRequest, context: Context): Exception? {
    val credentialManager = CredentialManager.create(context)
    val failureMessage = "Sign in failed!"
    var e: Exception? = null
    delay(250)
    try {
        val result = credentialManager.getCredential(
            request = request,
            context = context,
        )
        Log.i("LoginActivity", result.toString())
        Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()
    } catch (ex: NoCredentialException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e("LoginActivity", "$failureMessage: No credentials found", ex)
        e = ex
    } catch (ex: GoogleIdTokenParsingException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e("LoginActivity", "$failureMessage: Issue parsing GoogleIdToken", ex)
        e = ex
    } catch (ex: GetCredentialCustomException) {
        Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
        Log.e("LoginActivity", "$failureMessage: Custom credential issue", ex)
        e = ex
    } catch (ex: GetCredentialCancellationException) {
        Toast.makeText(context, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
        Log.e("LoginActivity", "$failureMessage: Cancelled", ex)
        e = ex
    }
    return e
}
