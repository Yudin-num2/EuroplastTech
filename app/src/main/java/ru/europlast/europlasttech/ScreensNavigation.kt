package ru.europlast.europlasttech

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.europlast.europlasttech.data.NetworkInterface
import ru.europlast.europlasttech.sockets.SocketScreen
import ru.europlast.europlasttech.sockets.SocketViewModel
import ru.europlast.europlasttech.sockets.SocketViewModelFactory

sealed class Screens(val route: String) {
    data object LoginScreen : Screens("login")
    data object MainScreen : Screens("main_screen")
    data object SocketsT01Screen : Screens("sockets_t01_screen")
    data object SocketsT02_7_Screen : Screens("sockets_t02_7_screen/{machine_name}")
    data object SocketsHusky: Screens("sockets_husky/{machine_name}")

    data object CurrentTasksScreen : Screens("current_tasks_screen")
    data object AddDefectScreen : Screens("add_defect_screen")
    data object AddAnomalyScreen : Screens("add_anomaly_screen")
    data object CheckListScreen : Screens("check_list_screen")
}


@Composable
fun Navigation() {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("http://10.0.2.2:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val networkAPI = retrofit.create(NetworkInterface::class.java)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.LoginScreen.route) {
        composable(Screens.LoginScreen.route) {
            LoginScreen(navController, networkAPI)
        }
        composable(Screens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(Screens.SocketsT01Screen.route) {
            SocketScreen(
                machineName = "Telerobot 1",
                list = listOf("Lid", "Frame", "Cutter", "Lid12", "Frame12", "Cutter12"),
                navController
            )
        }
        composable(Screens.SocketsT02_7_Screen.route) { navBackStackEntry ->
            val machineName = navBackStackEntry.arguments?.getString("machine_name")
            machineName?.let { title ->
                SocketScreen(
                    machineName = title,
                    list = listOf("Lid", "Frame", "Cutter"),
                    navController
                )
            }
        }
        composable(Screens.SocketsHusky.route) { navBackStackEntry ->
            val machineName = navBackStackEntry.arguments?.getString("machine_name")
            machineName?.let { title ->
                SocketScreen(
                    machineName = title,
                    list = listOf("Lid", "Frame", "Cutter"),
                    navController
                )
            }
        }
        composable(Screens.CurrentTasksScreen.route) {
            CurrentTasksScreen(
                navController, networkAPI)}
        composable(Screens.AddDefectScreen.route) {
            AddDefectScreen(navController)
        }
        composable(Screens.AddAnomalyScreen.route) {
            AddAnomalyScreen(navController)
        }
        composable(
            route = "checklist_screen/{techCard}",
            arguments = listOf(navArgument("techCard") { type = NavType.StringType })
        ) {navBackStackEntry ->
            val techCard = navBackStackEntry.arguments?.getString("techCard")
            techCard?.let { techCardName ->
                CheckListScreen(navController, networkAPI, techCardName)
            }
        }

    }
}




