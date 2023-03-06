package com.cyberwalker.fashionstore.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cyberwalker.fashionstore.R
import com.cyberwalker.fashionstore.util.showMessage
import com.cyberwalker.fashionstore.ui.theme.medium_18
import com.cyberwalker.fashionstore.util.LoadingState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginSCreen(
    viewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController,
    onAction: (actions: LoginScreenActions) -> Unit
) {
    Scaffold(scaffoldState = scaffoldState) { innerPadding ->
        LoginScreenContent(
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding),
            onAction = onAction
        )
    }
}

@Composable
private fun LoginScreenContent(
    viewModel: LoginViewModel,
    modifier: Modifier,
    onAction: (actions: LoginScreenActions) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxHeight()
            .semantics { contentDescription = "Login Screen" }) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val snackbarHostState = remember { SnackbarHostState() }
        val state by viewModel.loadingState.collectAsState()

        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            label = { Text(text = "Enter Email") },
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            textStyle = MaterialTheme.typography.medium_18,
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            label = { Text(text = "Enter Password") },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
        )
        Spacer(modifier = Modifier.size(16.dp))
        val context = LocalContext.current
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = email.isNotEmpty() && password.isNotEmpty(),
            content = {
                Text(text = "Sign in with your email/password")
            },
            onClick = {
                viewModel.signInWithEmailAndPassword(email.trim(), password.trim())
                showMessage(context, "Sign in with your email/password")
            })
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.caption,
            text = "Login with"
        )
        Spacer(modifier = Modifier.size(16.dp))

        val token = stringResource(R.string.default_web_client_id)
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    viewModel.signWithCredential(credential)
                } catch (e: ApiException) {
                    Log.w("TAG", "Google sign in failed", e)
                }
            }

        OutlinedButton(
            border = ButtonDefaults.outlinedBorder.copy(width = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                showMessage(context, "Sign in with your Gmail account")
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            },
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            tint = Color.Unspecified,
                            painter = painterResource(id = com.firebase.ui.auth.R.drawable.googleg_standard_color_18),
                            contentDescription = null,
                        )
                        Text(
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onSurface,
                            text = "Google"
                        )
                        Icon(
                            tint = Color.Transparent,
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = null,
                        )
                    })
            })
        when (state.status) {
            LoadingState.Status.SUCCESS -> {
              showMessage(context = context, message = "Success")
                   onAction(LoginScreenActions.Home)
            }
            LoadingState.Status.FAILED -> {
                showMessage(context = context, message =state.msg ?: "Error")
            }
            else -> {}
        }
    }



}

sealed class LoginScreenActions {
    object Home : LoginScreenActions()
}
