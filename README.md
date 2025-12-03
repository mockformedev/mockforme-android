# Mockforme Android Library

Mockforme is a powerful Android library that allows you to intercept and **mock apis** on the fly. It integrates seamlessly with **OkHttp and Retrofit**, enabling you to define API mappings and rules via the [Mockforme Dashboard](https://dashboard.mockforme.com) and apply them to your Android application **without changing your API endpoints or code logic**. Just enable mock APIs in the dashboard, and they start working instantly!


## Features

- **Remote Mocking:** Fetch API mappings and rules from the Mockforme dashboard.
- **OkHttp Integration:** Works as a standard OkHttp Interceptor.
- **Flexible Rules:** Support for delays, timeouts, and redirects.
- **Secure:** Encrypted response handling.
- **Broad Compatibility:** Supports Android 7.0+ (API 24+) and OkHttp 3.x & 4.x.

## Installation

Add the dependency to your module's `build.gradle.kts` file:

```kotlin
dependencies {
    // Add Mockforme
    implementation("com.mockforme:mockforme-android:1.0.7")

    // IMPORTANT: You MUST provide your own OkHttp dependency.
    // Mockforme supports both OkHttp 3.x and 4.x.
    implementation("com.squareup.okhttp3:okhttp:4.12.0") 
    // OR
    // implementation("com.squareup.okhttp3:okhttp:3.14.9")
}
```

## Quick Start

### 1. Initialize Mockforme

You must initialize the library with your access token **before** making any network requests. A good place to do this is in your `Application` class or main Activity.

```kotlin
import com.mockforme.Mockforme

// ... inside your initialization logic
Mockforme("YOUR_ACCESS_TOKEN").run(
    successCallback = { mappings ->
        Log.d("Mockforme", "Mappings loaded: ${mappings.size}")
    },
    errorCallback = { error ->
        Log.e("Mockforme", "Initialization failed", error)
    }
)
```

### 2. Add the Interceptor

Add `MockformeInterceptor` to your `OkHttpClient` builder.

```kotlin
import com.mockforme.MockformeInterceptor
import okhttp3.OkHttpClient

val client = OkHttpClient.Builder()
    .addInterceptor(MockformeInterceptor())
    // ... other interceptors
    .build()
```

### 3. Retrofit Integration

Mockforme integrates easily with Retrofit. Simply build your `OkHttpClient` with the `MockformeInterceptor` as shown above, and then pass that client to your Retrofit builder.

```kotlin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 1. Create the OkHttpClient with Mockforme
val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(MockformeInterceptor())
    .build()

// 2. Pass it to Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.yourdomain.com/")
    .client(okHttpClient) // <--- Important!
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

**Note:** If you add the interceptor but fail to call `Mockforme.run()` (or if it hasn't completed yet), the interceptor will throw an `IllegalStateException` to prevent silent failures.

## Advanced Configuration

### ProGuard / R8

The library includes its own consumer ProGuard rules, so you generally don't need to add anything. However, if you face issues, ensure you keep the public API:

```proguard
-keep class com.mockforme.Mockforme { *; }
-keep class com.mockforme.MockformeInterceptor { *; }
```

## Troubleshooting

- **Crash `NoSuchFieldError: ... toHttpUrl`:** This means you are using OkHttp 3.x but the library was expecting 4.x. **Update to Mockforme 1.0.7+** which fixes this issue and supports both versions.
- **`IllegalStateException: MockformeInterceptor is not initialized`:** You forgot to call `Mockforme("...").run()` or it failed. Check your `errorCallback`.
- **Mappings not applying:** Ensure your `MockformeInterceptor` is added **before** any logging interceptors if you want to see the mocked response in logs, or **after** if you want to see the original request.

## Community & Support

- **Dashboard:** [Create Mock APIs](https://dashboard.mockforme.com)
- **YouTube:** [Subscribe for Tutorials](https://www.youtube.com/@mockforme)
- **LinkedIn:** [Connect with Us](https://www.linkedin.com/in/mockforme-developer-523a53380/)

> **Quick Mock APIs:** Just initialize Mockforme and add the interceptor. That's it! <br/>
> ***Initialize. Intercept. Done.***

**Keywords:** `mock-apis`, `android-library`, `okhttp-interceptor`, `retrofit-mocking`, `api-testing`, `network-mocking`

## License

Copyright 2025 Mockforme. All rights reserved.
