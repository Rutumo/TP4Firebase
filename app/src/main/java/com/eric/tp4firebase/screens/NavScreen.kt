package com.eric.tp4firebase.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eric.tp4firebase.notification.NotificationMessage
import com.eric.tp4firebase.viewmodeles.AuthViewModele


sealed class DestinationScreen(val route: String) {
    object Main: DestinationScreen("main")
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Comment: DestinationScreen("comment")
    object Details: DestinationScreen("detail")
}


@Composable
fun AuthenticationApp() {
    val authvm = hiltViewModel<AuthViewModele>()
    val navController = rememberNavController()

    NotificationMessage(authvm)

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Main.route
    ) {
        composable(DestinationScreen.Main.route) {
            MainScreen(navController, authvm)
        }
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController, authvm)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController, authvm)
        }
        composable(DestinationScreen.Comment.route) {
            CommentsScreen(navController, authvm)
        }
        composable(DestinationScreen.Details.route) {
            ShowDetailsScreen(navController, authvm)
        }
    }
}

