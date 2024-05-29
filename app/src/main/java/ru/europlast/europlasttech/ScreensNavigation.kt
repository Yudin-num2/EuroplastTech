package ru.europlast.europlasttech

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.europlast.europlasttech.sockets.SocketScreen

sealed class Screens(val route: String) {
    data object LoginScreen : Screens("login")
    data object MainScreen : Screens("main_screen")
    data object SocketsT01Screen : Screens("sockets_t01_screen")
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(Screens.SocketsT01Screen.route) {
            SocketScreen(navController)
        }
    }
}
