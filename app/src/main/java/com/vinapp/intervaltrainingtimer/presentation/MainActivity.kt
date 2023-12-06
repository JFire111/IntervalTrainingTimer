package com.vinapp.intervaltrainingtimer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vinapp.intervaltrainingtimer.presentation.timer_screen.TimerScreen
import com.vinapp.intervaltrainingtimer.presentation.timer_editor_screen.TimerEditorScreen
import com.vinapp.intervaltrainingtimer.presentation.timer_list_screen.TimerListScreen
import com.vinapp.intervaltrainingtimer.ui_components.theme.AppTheme

class MainActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.TimerListScreen.route,
                ) {
                    composable(
                        route = Screen.TimerListScreen.route
                    ) {
                        TimerListScreen(
                            navigateToTimerEditorScreen = { timerId ->
                                navController.navigate(
                                    route = "${Screen.TimerEditorScreen.route}/?timerId=$timerId"
                                )
                            },
                            navigateToTimerScreen = { timerId ->
                                navController.navigate(
                                    route = "${Screen.TimerScreen.route}/$timerId"
                                )
                            },
                        )
                    }
                    composable(
                        route = "${Screen.TimerEditorScreen.route}/?timerId={timerId}",
                        arguments = listOf(navArgument("timerId") {
                            nullable = true
                            type = NavType.StringType
                        })
                    ) {
                        TimerEditorScreen(
                            timerId = it.arguments?.getString("timerId"),
                            navigateToTimerScreen = { timerId ->
                                navController.navigate(
                                    route = "${Screen.TimerScreen.route}/$timerId"
                                )
                            },
                            navigateBack = navController::popBackStack
                        )
                    }
                    composable(
                        route = "${Screen.TimerScreen.route}/{timerId}",
                        arguments = listOf(navArgument("timerId") {
                            type = NavType.StringType
                        })
                    ) {
                        it.arguments?.getString("timerId")?.let { timerId ->
                            TimerScreen(
                                timerId = timerId,
                            )
                        }
                    }
                }
            }
        }
    }
}