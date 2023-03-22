/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.cyberwalker.fashionstore.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cyberwalker.fashionstore.data.item1
import com.cyberwalker.fashionstore.detail.DetailScreen
import com.cyberwalker.fashionstore.detail.DetailScreenActions
import com.cyberwalker.fashionstore.dump.animatedComposable
import com.cyberwalker.fashionstore.home.HomeScreen
import com.cyberwalker.fashionstore.home.HomeScreenActions
import com.cyberwalker.fashionstore.login.LoginSCreen
import com.cyberwalker.fashionstore.login.LoginScreenActions
import com.cyberwalker.fashionstore.profile.ProfileScreen
import com.cyberwalker.fashionstore.splash.SplashScreen
import com.cyberwalker.fashionstore.splash.SplashScreenActions
import com.cyberwalker.fashionstore.util.showMessage
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

sealed class Screen(val name: String, val route: String) {
    object Splash : Screen("splash", "splash")
    object Home : Screen("home", "home")
    object Detail : Screen("detail", "detail")
    object Login : Screen("login", "login")

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FashionNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    actions: NavActions = remember(navController) {
        NavActions(navController)
    }
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        animatedComposable(Screen.Splash.route) {
            SplashScreen(onAction = actions::navigateToLogin)//navigateToHome)
        }

        animatedComposable(Screen.Login.route) {
            LoginSCreen(onAction = actions::navigateFromLogin, navController = navController)
        }

        animatedComposable(Screen.Home.route) {
            HomeScreen(onAction = actions::navigateFromHome,navController = navController)
        }

        animatedComposable(Screen.Detail.route) {
            DetailScreen(onAction = actions::navigateFromDetails/*, item = item1*/)
        }
        
//        composable(BottomNavItems.Home.screen_route){
//            HomeScreen(onAction ={} , navController = navController)
//        }
        
        composable(BottomNavItems.Profile.screen_route){
            ProfileScreen( navController = navController)
        }
        
        composable("${SHOW_DETAIL_SCREEN}/{item}",
        arguments = listOf(navArgument("iten"){type=NavType.StringType})){
         DetailScreen(onAction = {})   
        }
    }
}

class NavActions(private val navController: NavController) {
    fun navigateToLogin(_A: SplashScreenActions) {
        navController.navigate(Screen.Login.name) {
            popUpTo(Screen.Splash.route){
                inclusive = true
            }
        }
//        fun navigateToHome(_A: SplashScreenActions) {
//        navController.navigate(Screen.Home.name) {
//            popUpTo(Screen.Splash.route){
//                inclusive = false
//            }
//        }
    }

    fun navigateFromLogin(actions: LoginScreenActions) {
        when (actions) {
            LoginScreenActions.Home -> {
                navController.navigate(Screen.Home.name)
            }
        }
    }
    fun navigateFromHome(actions: HomeScreenActions) {
        when (actions) {
            HomeScreenActions.Details -> {
                navController.navigate(Screen.Detail.name)
            }
        }
    }

    fun navigateFromDetails(actions: DetailScreenActions) {
        when(actions) {
            DetailScreenActions.Back -> navController.popBackStack()
        }
    }
}



//@Composable
//fun NavGraph(
//    navController: NavHostController,
////    studentsViewModel: StudentsViewModel
//) {
//    NavHost(navController, startDestination = BottomNavItems.Students.screen_route) {
//        composable(BottomNavItems.Students.screen_route) {
//            StudentsListScreen(navController, studentsViewModel)
//        }
//        composable(BottomNavItems.Actors.screen_route) {
////            Content(title = "Actors")
//        }
//
//        composable("${SHOW_DETAIL_SCREEN}/{student}",
//            arguments = listOf(navArgument("student"){type= NavType.StringType})
//        ) {
//            StudentScreen(navController=navController,student = studentsViewModel.selectedStudent.value)  
//        //studentsViewModel.selectedStudent.value?.let { it1 -> StudentScreen( student = it1) }
//        }
//    }
//}
