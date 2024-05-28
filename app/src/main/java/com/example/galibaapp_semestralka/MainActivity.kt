package com.example.galibaapp_semestralka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.galibaapp_semestralka.navigation.Start
import com.example.galibaapp_semestralka.ui.theme.GalibaAppSemestralkaTheme

class  MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalibaAppSemestralkaTheme {

            Start()

            }
        }
    }
}