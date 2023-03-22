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
package com.cyberwalker.fashionstore.dump

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.navigation.Screen
import com.cyberwalker.fashionstore.ui.theme.bottomNavbg
import com.cyberwalker.fashionstore.ui.theme.highlight
import com.cyberwalker.fashionstore.util.showMessage

@Composable
fun BottomNav(navController: NavController, isDark: Boolean = isSystemInDarkTheme()) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Liked,
        BottomNavItem.Profile,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.bottomNavbg,
        contentColor = highlight
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val con = LocalContext.current
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon),
                    contentDescription = item.title) },
                selectedContentColor = highlight,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route){
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it){}
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    showMessage(con,"click")
                }
            )
        }
    }
}

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {

    object Home : BottomNavItem("Home", R.drawable.home, Screen.Home.route)
    object Search : BottomNavItem("Search", R.drawable.search, "Search")
    object Liked : BottomNavItem("Liked", R.drawable.liked, "Liked")
    object Profile : BottomNavItem("Profile", R.drawable.profile, "Profile")
}








//
//@Composable
//fun BottomNav(navController: NavController) {
//    val items = listOf(
//        BottomNavItems.Students,
//        BottomNavItems.Actors
//    )
//    BottomNavigation(
//        backgroundColor = colorResource(id = R.color.black),
//        contentColor = Color.White
//    ){
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        items.forEach { item ->
//            BottomNavigationItem(
//                icon = { Icon(
//                    painterResource(id = item.icon), contentDescription = item.title,
//                    modifier = Modifier.size(30.dp)
//                ) },
//                label = { Text(text = item.title,
//                    fontSize = 16.sp) },
//                selectedContentColor = Color.White,
//                unselectedContentColor = Color.White.copy(0.6f),
//                alwaysShowLabel = true,
//                selected = currentRoute == item.screen_route,
//                onClick = {
//                    navController.navigate(item.screen_route) {
//
//                        navController.graph.startDestinationRoute?.let { screen_route ->
//                            popUpTo(screen_route) {
//                                // saveState = true
//                            }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    }
//}
//
//sealed class BottomNavItems(var title:String, var icon:Int, var screen_route:String){
//
//    object Students : BottomNavItems("Students",R.drawable.ic_baseline_home_24, STUDENTS_SCREEN)
//    object Actors : BottomNavItems("Actors", R.drawable.ic_baseline_actors, ACTORS_SCREEN)
//}