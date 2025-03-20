package com.example.tpgdemo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tpgdemo.ui.CatsPhotosApp
import com.example.tpgdemo.ui.theme.CatsPhotoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CatsPhotoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CatsPhotosApp()
                }
            }
        }
    }
}