# Facebook Navigator

**Facebook Navigator** is a specialized, modern, and lightweight Android portal app designed to provide seamless, secure, and private access to Facebook via Android Custom Tabs.

## Key Features

- **Privacy-First Architecture**: Zero credential storage. All logins and private messages are handled strictly through Facebook's official HTTPS servers via Android Custom Tabs.
- **Modern Jetpack Compose UI**: Built with Material 3 design, dynamic theming, smooth transitions, and responsive layout.
- **Quick Navigation**: Instant one-tap access to News Feed, Messages, Notifications, Marketplace, Watch & Reels, and Groups.
- **Network State Monitor**: Real-time connectivity awareness with automatic retry mechanisms and error notifications.
- **Configurable Preferences**: Toggle Android Custom Tabs, set auto-open on launch, and customize browser behavior.

## Tech Stack

- **Language**: Kotlin 2.2.10
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model-View-ViewModel) with Android Architecture Components & Coroutines Flow
- **Browser Integration**: AndroidX Browser (Custom Tabs)
- **Build System**: Gradle 9.6.0 with Android Gradle Plugin 9.5.1
- **Target SDK**: Android 16 (API 36)
- **Min SDK**: Android 8.0 (API 26)

## Building & Installing

### Prerequisites
- Android SDK with API 36
- JDK 17+

### Build Debug APK
```bash
./gradlew assembleDebug
```
The APK will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

### Install on Device via ADB
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Disclaimer
Facebook is a trademark of Meta Platforms, Inc. This application is an independent client tool and is not affiliated with, authorized, or endorsed by Meta Platforms, Inc.
