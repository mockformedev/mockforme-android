package com.mfm.mockforme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.mfm.mockforme.model.Product
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
        Mockforme().run(
            successCallback = { response  ->
                println("Mappings: $response")
                Log.d("MockformeMappings", response.toString())
            },
            errorCallback = { err ->
                Log.d("MockformeError", err.message.toString())
            },
            context = this
        );

        setContent {
            MockformeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(
                            modifier = Modifier.padding(innerPadding)
                        )

                        Row() {
                            Button(
                                onClick = {
                                    lifecycleScope.launch {
                                        try {
                                            val users: List<User> = userService.getUsers()
                                            Log.d("Users", users.toString())
                                        } catch (e: Exception) {
                                            Log.d("Exception", e.toString())
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = "Get Users",
                                    fontSize = 40.sp
                                )
                            }
                        }


                        Row(modifier = Modifier.padding(10.dp)) {
                            Button(
                                onClick = {
                                    lifecycleScope.launch {
                                        try {
                                            val products: List<Product> = userService.getProducts()
                                            Log.d("Products", products.toString())
                                        } catch (e: Exception) {
                                            Log.d("Exception", e.toString())
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = "Get Products",
                                    fontSize = 40.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Row(modifier = Modifier) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "DEMO",
                modifier = modifier,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "MockForMe DevTool",
                modifier = modifier,
                fontSize = 40.sp,
                lineHeight = 60.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MockformeTheme {
        Greeting()
    }
}