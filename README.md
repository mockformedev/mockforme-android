# Seamless API Mocking for Android with MockForMe
This repository provides a working Android example demonstrating how to integrate `MockForMe` in an Android app for **API mocking**. Using the **mockforme-android library**, developers can **simulate API responses** without modifying the backend, making **Android API testing**, **OkHttp network requests**, and **Kotlin integration** faster and more reliable.

This project shows the complete setup to integrate **MockForMe** with **OkHttp** in an Android application, enabling **mock API responses**, **frontend API testing**, and **rapid prototyping**.


> **Note:**  
> This example uses **OkHttp** and **kotlinx-coroutines** to demonstrate how to mock APIs with **MockForMe**.
> - `OkHttp` → for making HTTP calls
> - `kotlinx-coroutines` → for handling HTTP calls asynchronously

## Step 1: Add Internet permission in `AndroidManifest.xml` file
```
<uses-permission android:name="android.permission.INTERNET" />
```

## Step 2: Add MockForMe Dependency
In your `gradle/libs.versions.toml` file:

- Inside the `[versions]` block, add:
  ```toml
  okhttp = "4.12.0"
  kotlinx-coroutines = "1.9.0"
  mockforme = "1.0.5"
  ```
- Inside the [libraries] block, add:
  ```toml
  okhttp = { module = "com.squareup.okhttp3:okhttp", version.ref = "okhttp" }
  coroutines = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "kotlinx-coroutines" }
  mockforme-android = { group = "com.mockforme", name = "mockforme-android", version.ref = "mockforme" }
  ```
## Step 3: Add MockForMe Dependency
In your `app/build.gradle.kts`, include the MockForMe library inside the `dependencies` block:

```kotlin
dependencies {
  // other dependencies...
  implementation(libs.okhttp)
  implementation(libs.coroutines)
  implementation(libs.mockforme.android)
  // other dependencies...
}
```
## Step 4: Add MockForMe Token in Configuration
In your `local.properties`, add:
  ```properties
  MFM_TOKEN=your_token_here
  ```
> **Note:** You can get the token from mockforme dashboard [https://dashboard.mockforme.com/user/token](https://dashboard.mockforme.com/user/token)


## Step 5: Expose Token via BuildConfig
In your `app/build.gradle.kts`, load the token from `local.properties` and define it inside the `android` → `defaultConfig` block so it becomes available as `BuildConfig.MOCKFORME_TOKEN`.
> **Note:**
> To read values from `local.properties`, you must import `java.util.Properties`. Additionally, the BuildConfig feature is disabled by default; enable it by adding `buildConfig = true` inside `android { buildFeatures { ... } }`.

  ```kotlin
  import java.util.Properties
  // some code...

  android {
    // some code...

    defaultConfig {
      // some code...

      val localProperties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
      }

      val token: String = localProperties.getProperty("MFM_TOKEN") ?: ""
      buildConfigField("String", "MOCKFORME_TOKEN", "\"$token\"")
    }
    // some code...

    buildFeatures {
      buildConfig = true
      compose = true
    }
  }
  ```
> After making these changes, run the `./gradlew clean` command in your terminal.
> This will clear old build artifacts and regenerate the `BuildConfig` file with the updated values.

## Step 6: Create a `Network` Class

Create a new file:  
`app/src/main/java/com/mfm/mockforme/Network.java`

> The `Network` class provides a singleton `OkHttpClient` instance that is shared across the entire application.

- **Debug builds** → `MockForMe.install(builder)` injects the interceptor to return mocked responses.
- **Release builds** → behaves as a regular `OkHttpClient` with no mocking enabled.


### Step 7: Configure `app/src/main/java/com/mfm/mockforme/MainActivity.java`

- When the app launches, `MockForMe(BuildConfig.MOCKFORME_TOKEN).run(...)` fetches the available mock API mappings.
- The **"Get User Info"** button triggers a network request to `https://api.example.com/user/me`.
  > The host can be anything — MockForMe only validates the **API path** (e.g., `/user/me`).
- In **Debug builds**, the response is served from **MockForMe**
  > **only if API mocking is enabled in the [MockForMe Dashboard](https://dashboard.mockforme.com/)**.
- In **Release builds**, it falls back to a real API call.


> To test this project, you need to create a mock API in the **MockForMe Dashboard**:
1. Visit [https://dashboard.mockforme.com](https://dashboard.mockforme.com)
2. Sign up or sign in with your Google account
3. Create a **Collection** (e.g., User)
4. Inside the collection, create an **API**:
   * Endpoint: `/user/me`
   * Method: `GET`
5. Enable the **Enable Mocking** switch
6. Add the desired **API responses**

> Next, **generate an access token** to use in your project:
1. Visit [https://dashboard.mockforme.com/user/token](https://dashboard.mockforme.com/user/token)
2. If a token does not already exist, click **Generate a new token**


### ✅ Key Points
- No code changes required between debug & release
- Debug build → APIs are mocked via MockForMe <br />
- Release build → APIs hit real servers <br />
- Works with OkHttp and Coroutines seamlessly <br />


## 🔒 Security & Privacy
MockForMe is designed with developer trust and data safety in mind.

- ✅ We **only intercept API requests** that you have explicitly enabled in the **MockForMe Dashboard**.
- ✅ We **do not access, store, or transmit any customer data** from your application.
- ✅ The library **only modifies the request path and attaches the MockForMe token** in order to return the configured mock response.


**Keywords:** mockforme, mockforme-android, android api mocking, okhttp mock api, android api testing, android mock api example, api mocking android
