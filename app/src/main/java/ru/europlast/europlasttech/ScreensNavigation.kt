package ru.europlast.europlasttech

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.europlast.europlasttech.data.CurrentTask
import ru.europlast.europlasttech.data.CurrentTasksList
import ru.europlast.europlasttech.sockets.SocketScreen

sealed class Screens(val route: String) {
    data object LoginScreen : Screens("login")
    data object MainScreen : Screens("main_screen")
    data object SocketsT01Screen : Screens("sockets_t01_screen")
    data object SocketsT02_7_Screen : Screens("sockets_t02_7_screen/{machine_name}")
    data object CurrentTasksScreen : Screens("current_tasks_screen")
    data object AddDefectScreen : Screens("add_defect_screen")
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
            SocketScreen(
                machineName = "Telerobot 1",
                list = listOf("Lid", "Frame", "Cutter", "Lid12", "Frame12", "Cutter12")
            )
        }
        composable(Screens.SocketsT02_7_Screen.route) { navBackStackEntry ->
            val machineName = navBackStackEntry.arguments?.getString("machine_name")
            machineName?.let { title ->
                SocketScreen(
                    machineName = title,
                    list = listOf("Lid", "Frame", "Cutter")
                )
            }
        }
        composable(Screens.CurrentTasksScreen.route) {
            CurrentTasksScreen(
                navController, tasksList = CurrentTasksList(
                    listOf(
                        CurrentTask(
                            task = "Заменить техническую оснастку на Телероботе №1",
                            workers = listOf("Worker1", "Worker2"),
                            status = "В работе",
                            techCard = "",
                            pathToPhoto = "",
                            createTime = "01-01-01 15:16:17.99999",
                            author = "Сысоев Илья",
                            spentRepairParts = listOf("Part1", "Part2")
                        ),
                        CurrentTask(
                            task = "Задача №2",
                            workers = listOf("Worker1", "Worker2"),
                            status = "В работе",
                            techCard = "",
                            pathToPhoto = "",
                            createTime = "01-01-01 15:16:17.99999",
                            author = "Сысоев Илья",
                            spentRepairParts = listOf("Part1", "Part2")
                        ),
                        CurrentTask(
                            task = "Задача №3",
                            workers = listOf("Worker1", "Worker2"),
                            status = "Выполнена",
                            techCard = "",
                            pathToPhoto = "",
                            createTime = "01-01-01 15:16:17.99999",
                            author = "Сысоев Илья",
                            spentRepairParts = listOf("Part1", "Part2")
                        ),
                    )
                )
            )
        }
        composable(Screens.AddDefectScreen.route) {
            AddDefectScreen()
        }
    }
}

