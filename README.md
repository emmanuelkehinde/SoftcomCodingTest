# Dynamic Form Generator

An android application that generates a dynamic form using data from a json file.

#### Architecture and Tools used

- [Kotlin](https://developer.android.com/kotlin) - Programming Language
- [MVVM](https://developer.android.com/topic/libraries/architecture) - Architecture
- [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store and manage UI-related data
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holder 
- [Dagger 2](https://google.github.io/dagger/) - Dependency Injection
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Asynchronous Programming
- [Picasso](https://github.com/square/picasso) - Image Loading
- [Mockito](https://site.mockito.org/), [Expresso](https://developer.android.com/training/testing/espresso), [Roboelectric](http://robolectric.org/) - Testing
- [Material Component](https://material.io/develop/android/docs/getting-started/), [Gson](https://github.com/google/gson), [Leak Canary](https://github.com/square/leakcanary), [Shutdown](https://github.com/emmanuelkehinde/Shutdown) - Others

#### How to use

- Clone project
- Import into Android Studio
- Add form json file to `assets/form`
- Update json file name in `Constants.kt`
- Build and Run
