# Mockforme Android Library

Mockforme is a powerful Android library that allows you to intercept and **mock apis** on the fly. It integrates seamlessly with **OkHttp and Retrofit**, enabling you to define API mappings and rules via the [Mockforme Dashboard](https://dashboard.mockforme.com) and apply them to your Android application **without changing your API endpoints or code logic**. Just enable mock APIs in the dashboard, and they start working instantly!

## Setup in 3 simple steps
**Add Mockforme dependency** → **Initialize Mockforme().run()** → **Add the interceptor**

## Features

- API Mocking via Dashboard
- Works with Retrofit & OkHttp
- No API or code changes required
- Delay, Timeout & Redirect rules
- Android 7.0+ support

## Installation

Add the dependency to your module's `build.gradle.kts` file:

```kotlin
dependencies {
    // Add Mockforme
    implementation("com.mockforme:mockforme-android:1.0.8")

    // IMPORTANT: You MUST provide your own OkHttp dependency.
    // Mockforme supports both OkHttp 3.x and 4.x.
    implementation("com.squareup.okhttp3:okhttp:4.12.0") 
    // OR
    // implementation("com.squareup.okhttp3:okhttp:3.14.9")
}
```

## Quick Start

### 1. Initialize Mockforme

Initialize Mockforme in your `Application` or main `Activity`.

```kotlin
import com.mockforme.Mockforme

// Use this inside your Application or MainActivity
Mockforme().run(
    context = this,
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
    .build()
```

## MockForMe DevTools

Mockforme comes with a floating **DevTools** widget that provides real-time control over your API mocking.

### DevTool widget

![DevTools Widget](https://ik.imagekit.io/mfm/static-collection/android-mfm.gif)

### Key Features

- **Floating DevTools**: A movable widget for quick access.
- **Access Token**: Add or update your token inside the app.
- **Reload Mocks**: Refresh mock APIs and rules with one tap.
- **API Inspector**: View all network requests in real time.
  - **Mocked**: Requests served with mock data.
  - **Not Mocked**: Normal API calls.
- **Error Indicators**: Easily spot failed or aborted requests.


## Troubleshooting

- **Crash `NoSuchFieldError: ... toHttpUrl`:** This means you are using OkHttp 3.x but the library was expecting 4.x. **Update to Mockforme 1.0.8+** which fixes this issue and supports both versions.
- **`IllegalStateException: MockformeInterceptor is not initialized`:** You forgot to call `Mockforme().run()`.
- **Mappings not applying:** Ensure your `MockformeInterceptor` is added **before** any logging interceptors if you want to see the mocked response in logs, or **after** if you want to see the original request.

## Community & Support

- **Dashboard:** [Create Mock APIs](https://dashboard.mockforme.com)
- **YouTube:** [Subscribe for Tutorials](https://www.youtube.com/@mockforme)
- **LinkedIn:** [Connect with Us](https://www.linkedin.com/in/mockforme-developer-523a53380/)

> **Quick Mock APIs:** Just initialize Mockforme and add the interceptor. That's it!

**Keywords:** `mock-apis`, `android-library`, `okhttp-interceptor`, `retrofit-mocking`, `api-testing`, `network-mocking`, `DevTools`

## License

Copyright 2025 Mockforme. All rights reserved.
