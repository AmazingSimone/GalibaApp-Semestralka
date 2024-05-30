package com.example.galibaapp_semestralka

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.galibaapp_semestralka.navigation.Start
import com.example.galibaapp_semestralka.ui.theme.GalibaAppSemestralkaTheme

class  MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalibaAppSemestralkaTheme {

                Start()

            }
        }
    }
}