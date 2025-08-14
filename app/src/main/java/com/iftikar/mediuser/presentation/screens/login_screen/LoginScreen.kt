package com.iftikar.mediuser.presentation.screens.login_screen

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.iftikar.mediuser.R
import com.iftikar.mediuser.navigation.Routes
import com.iftikar.mediuser.presentation.components.textfield.LoginButtonComponent
import com.iftikar.mediuser.presentation.components.textfield.TextFieldComponent

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = loginViewModel.loginState.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("") }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var buttonTextColor by remember { mutableStateOf<Color>(Color.Black) }

    var loginErrorText by remember { mutableStateOf("") }

    // Animated background gradient that adapts to the current Material theme
    val infiniteTransition = rememberInfiniteTransition(label = "bgTransition")
    val shift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 9000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bgShift"
    )
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_animation))

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val width = size.width
                    val height = size.height
                    val start = Offset(x = 0f, y = height * shift)
                    val end = Offset(x = width, y = height * (1f - shift))
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = gradientColors,
                            start = start,
                            end = end
                        )
                    )
                },
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(8.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = composition,
                    isPlaying = true,
                    iterations = LottieConstants.IterateForever,
                    speed = 0.6f,
                    restartOnPlay = false,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )

                TextFieldComponent(
                    value = email,
                    onTextChange = {
                        emailError = null
                        email = it
                    },
                    title = if (emailError != null) emailError else "Email",
                    color = if (emailError != null) Color.Red else Color.Unspecified,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    }
                )

                Spacer(Modifier.height(10.dp))

                TextFieldComponent(
                    value = password,
                    onTextChange = {
                        passwordError = null
                        password = it
                    },
                    title = if (passwordError != null) passwordError else "Password",
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    color = if (passwordError != null) Color.Red else Color.Unspecified,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Password,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        val icon =
                            if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null
                            )
                        }
                    }
                )

                LoginButtonComponent(
                    text = buttonText,
                    textColor = buttonTextColor
                ) {
                    if (email.isBlank() || password.isBlank()) {
                            if (email.isBlank()) {
                                emailError = "Please enter your email"
                            }
                            if (password.isBlank()) {
                                passwordError = "Please enter your password"
                            }
                            return@LoginButtonComponent
                        }

                        loginViewModel.login(email, password)
                }

                Text(text = loginErrorText, color = Color.Red)

                when (val viewState = state.value) {
                    is LoginState.Error -> {
                        loginErrorText = viewState.message
                        buttonText = "Try again"
                        buttonTextColor = Color.Red
                    }

                    LoginState.Idle -> {
                        buttonText = "log in"
                        buttonTextColor = Color.Black
                    }

                    LoginState.Loading -> {
                        buttonText = "logging in"
                        buttonTextColor = Color.Black
                        loginErrorText = ""
                    }

                    is LoginState.Success -> {
                        Log.d(
                            "Login",
                            "${viewState.loginResponse.message}: ${viewState.loginResponse.status}"
                        )
                        LaunchedEffect(Unit) {
                            navHostController.navigate(Routes.WaitingScreen(viewState.loginResponse.message)) {
                                popUpTo<Routes.LoginScreen> { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}
