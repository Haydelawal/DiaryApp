# This is the complete and final project. 

The app uses mongodb, firebase, room and koin to for the diary app.
The app is modularized into 3 main modules:
- core (ui and util)
- data (mongo db)
- feature (auth, home and write)

I would suggest some mods that I am currently too busy to make myself. Good quality PR's are very well welcomed and appreciated, such as:
- stevdza sans one tap compose library has been deprecated
- stevdza sans  messagebarcompose library has been deprecated
- app currently uses build.gradle rather than build.gradle.kts
- using this version of kotlinxCoroutinesCore = { strictly = "1.6.0-native-mt" }
- using compose navigation library that still uses routes (theres type safe version)
- NetworkConnectivityObserver can be injected via DI (dependency injection)
- and others as you see fit
