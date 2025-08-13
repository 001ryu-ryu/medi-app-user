package com.iftikar.mediuser.presentation.components.textfield

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun LoginButtonComponent(
    text: String,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = textColor
        )
    }
}