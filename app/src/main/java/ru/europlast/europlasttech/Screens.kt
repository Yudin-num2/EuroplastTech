package ru.europlast.europlasttech

sealed class Screens(val route: String) {
    data object LoginScreen : Screens("login")
    data object MainScreen : Screens("main_screen")
}



