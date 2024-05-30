package com.sparrow.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sparrow.movieapp.ui.HomeScreen
import com.sparrow.movieapp.ui.MovieDetailScreen
import com.sparrow.movieapp.ui.SplashScreen

@Composable
fun NavigationHost() {
    var navigationCntr = rememberNavController()

    NavHost(navController = navigationCntr, startDestination = "splash_screen") {
        composable("splash_screen"){
            SplashScreen(navigationCntr=navigationCntr)
        }

        composable("home_screen"){
            HomeScreen(navigationCntr=navigationCntr)
        }
        composable("movie_detail/{id}", arguments = listOf(
            navArgument(name = "id"){
                type = NavType.IntType
            }
        ) ){
            it.arguments?.getInt("id")?.let {id->
                MovieDetailScreen(id)

            }



        }
    }
}