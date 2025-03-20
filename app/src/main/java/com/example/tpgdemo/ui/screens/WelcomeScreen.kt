package com.example.tpgdemo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WelcomeScreen(
    modifier: Modifier,
    onPublicClick: () -> Unit,
    onUploadClick: () -> Unit
){

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray), // Dark Grey Background
        contentAlignment = Alignment.Center
    ) {

            Column(

                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                SquareButton(
                    text = "Uploads",
                    onClick = onUploadClick
                )
                SquareButton(
                    text = "Public Gallery",
                    onClick = onPublicClick
                )

        }

    }
}

@Composable
fun SquareButton(text: String, onClick: () -> Unit) {
    Button(
        border =
        ButtonDefaults.outlinedButtonBorder,
        onClick = onClick,
        elevation =  ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
        shape =  RoundedCornerShape(20.dp),
        modifier = Modifier
            .width( 200.dp)
            .height(100.dp)
           ,
        colors = ButtonDefaults.buttonColors( Color.Blue.copy(alpha = 0.5f)) // Change button color
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}