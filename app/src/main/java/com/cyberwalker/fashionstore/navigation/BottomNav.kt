package com.cyberwalker.fashionstore.navigation

import com.cyberwalker.fashionstore.R



import androidx.compose.foundation.layout.size
import androidx.navigation.NavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState



@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.Profile,
        BottomNavItems.Search,
        BottomNavItems.Liked,

    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.black),
        contentColor = Color.White
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(
                    painterResource(id = item.icon), contentDescription = item.title,
                    modifier = Modifier.size(30.dp)
                ) },
                label = { Text(text = item.title,
                    fontSize = 16.sp) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.6f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                // saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

sealed class BottomNavItems(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItems("Home",R.drawable.home, HOME_SCREEN)
    object Profile : BottomNavItems("Profile", R.drawable.profile, PROFILE_SCREEN)
    object Search: BottomNavItems("search", R.drawable.search, SEARCH_SCREEN)
    object Liked: BottomNavItems("liked", R.drawable.liked, LIKED_SCREEN)

//    object Home : Screen("home", "home")
//    object Profile: Screen("profile", "profile")
}