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
package com.cyberwalker.fashionstore.splash

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.ui.theme.dark
import com.cyberwalker.fashionstore.ui.theme.large
import com.cyberwalker.fashionstore.ui.theme.small_caption
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onAction: (actions: SplashScreenActions) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        SplashScreenContent(
            modifier = Modifier.padding(innerPadding),
            onAction = onAction,
            viewModel = viewModel
        )
    }
}

@Composable
private fun SplashScreenContent(
    modifier: Modifier,
    onAction: (actions: SplashScreenActions) -> Unit,
    viewModel: SplashViewModel,
) {
    Column(
        modifier = modifier
            .padding(40.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Splash Screen" }
    ) {
//        val permissionState =  rememberMultiplePermissionsState(
//            permissions = listOf(
//                Manifest.permission.POST_NOTIFICATIONS,
//                Manifest.permission.CAMERA
//            ))


        val user = viewModel.user
        val activity = (LocalContext.current as? Activity)
        Text(
            text = "Find and shop your fashion products here.",
            style = MaterialTheme.typography.large.copy(
                shadow = Shadow(
                    color = dark,
                    offset = Offset(0.0f, 4.0f),
                    blurRadius = 5f
                )
            )
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Welcome to our store", style = MaterialTheme.typography.small_caption)
        Image(
            modifier = Modifier
                 .defaultMinSize(minWidth = 293.dp, minHeight = 387.dp)
//                .weight(1F)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(16.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_indicator),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(16.dp))
        Image(
            modifier = Modifier
//                .weight(0.25F)
                .align(Alignment.CenterHorizontally)
                .clickable {
//                    throw RuntimeException("Test Crash") // Force a crash
//                    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101,  activity!!)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { checkPermission(Manifest.permission.POST_NOTIFICATIONS, 101,  activity!!) }
                    if (user == null) onAction(SplashScreenActions.LoadLogin)
                    else onAction(SplashScreenActions.LoadHome)
                },
            painter = painterResource(id = R.drawable.splash_cta),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

sealed class SplashScreenActions {
    object LoadHome : SplashScreenActions()
    object LoadLogin : SplashScreenActions()
}


fun checkPermission(permission: String, requestCode: Int, activity: Activity) {
    // on below line we are checking if the permission is denied.
    if (ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_DENIED
    ) {
        // if the permission is denied we are calling
        // request permission method to request permissions.
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)

    } else {
        // this method will be called if the permissions are already granted.
        // On below line we are displaying a toast message if permissions are granted.
        Toast.makeText(activity, "Permission already granted..", Toast.LENGTH_SHORT).show()
    }
}

