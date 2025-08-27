package com.mfm.mockforme

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mfm.mockforme.ui.theme.MockformeTheme
import com.mockforme.ApiMatcher
import com.mockforme.MockForMe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        /**
         * Initialize mockforme to get the mocked apis in the mockforme dashboard
         */
        if (BuildConfig.DEBUG) {
            MockForMe(BuildConfig.MOCKFORME_TOKEN).run(onSuccess = { apiMappings: List<ApiMatcher.ApiMapping> ->
                println(apiMappings)
            }, onError = { e ->
                println (e.toString())
            })
        }

        setContent {
            MockformeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val scope1 = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 10.dp, end = 50.dp, bottom = 0.dp, start = 50.dp),
        ) {
            Text(
                text = "$name!"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .padding(top = 50.dp, end = 50.dp, bottom = 50.dp, start = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                scope1.launch {
                    try {
                        val body = withContext(Dispatchers.IO) {
                            val request = Request.Builder()
                                .url("https://api.example.com/user/me")
                                .get()
                                .build()

                            Network.getClient().newCall(request).execute().use { resp ->
                                if (!resp.isSuccessful) {
                                    "HTTP ${resp.code}: ${resp.message}"
                                } else {
                                    resp.body?.string().orEmpty()
                                }
                            }
                        }
                        Log.d("HTTP_RESPONSE", body)
                        println(body.toString())
                    } catch (e: Exception) {
                        Log.d("HttpRequestFailed", e.toString())
                    }
                }
            }) {
                Text("Get User Info")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MockformeTheme {
        Greeting("Mockforme Android Example")
    }
}