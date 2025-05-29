package edu.ucne.ap2_p1_almarosa.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun HostNavigation(
    navHostController: NavHostController,

    ){
    NavHost (
        navHostController = navHostController,
        startDestination = Screen.List
    ){  }
}