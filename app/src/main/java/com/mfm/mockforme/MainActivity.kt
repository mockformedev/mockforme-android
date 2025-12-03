package com.mfm.mockforme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.mfm.mockforme.model.User
import com.mfm.mockforme.service.RetrofitInstance
import com.mfm.mockforme.ui.theme.MockformeTheme
import com.mockforme.Mockforme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    val userService = RetrofitInstance.getUserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /**
         * Get access token from:
         * https://dashboard.mockforme.com/user/token
         */
        Mockforme("YOUR_ACCESS_TOKEN").run(successCallback = { apiMappings ->
            print(apiMappings.toString())
            Log.d("MockformeMappings", apiMappings.toString())
        },
        errorCallback = { err ->
            Log.d("MockformeError", err.message.toString())
        })

        setContent {
            MockformeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    val users: List<User> = userService.getUsers()
                                    Log.d("Users", users.toString())
                                }
                            }
                        ) {
                            Text(
                                text = "Get Users",
                                fontSize = 40.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MockformeTheme {
        Greeting("Android")
    }
}