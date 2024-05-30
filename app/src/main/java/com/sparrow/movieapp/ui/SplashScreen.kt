package com.sparrow.movieapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sparrow.movieapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigationCntr: NavHostController) {

    LaunchedEffect(key1 = true) {
        delay(2000)
        navigationCntr.navigate("home_screen"){
            popUpTo(0)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.clapperboard), contentDescription = "logo")
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "MovieDB", fontSize = 25.sp, fontFamily = FontFamily(Font(R.font.poppins)), color = Color.White)
    }
}