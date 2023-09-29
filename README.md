# PremiumMovies

This app is build using kotlin, jetpack libraries, jetpack compose, kts gradle, unit tests and instrumentation tests.

This is a sample app that includes screens to: 
 - View Trending movies, filter by quering and by chip filtering; 
 - View movies Details;

UX flow Description:
  - User can view Trending Movies from a remote endpoint
  - Movies fetched from remote datasource are saved to our local sqlite database
  - Users can query search and filter movies which are fetched locally from SQLITE BY QUERYING
  - User can load more data, if the device is connected to the internet, from the remote endpoint
  - User can view a single movie details which will navigate to a new screen : MovieDetailsScreen


SDKs and Languages used :
- Kotlin
- Android SDK

Jetpack Library
- Jetpack compose
- Coroutines, Flows and MutableStates
- Navigation in Compose
- ViewModels


Architecures and patterns :
- MVVM
- Repository pattern
- Dependency Injection using Dagger-Hilt, integrated with viewmodels and composables
- A layer of abstraction between Data layer and Presention Layer and Domain Layer using interfaces (Using Reposiotry & RepositoryImpl)
- SOLID principles

Compose:
- Material-3 Design
- State management for composables
- Re-usable compose features

- I have implemented a sample work for :
  - UnitTests (MockRepositories and viewmodels), mainly for testing viewmodels
  - Instrumented Tests ( ex : to test DAOs in Room Database)


 Git :
   - Used github, github actions and firebase distribution for CI.
   - Created two main branches, develop, which most of the work was pushed to, and then Master, that is the main branch from which the CI flow launches, please check the history of both branches.

- CI (Using firebase app distribution and github actions)
  - Created a github actions workflow (.yaml), when pushing to master branch, to build the debug APK, check for warning, run the Unit test and Intrumented tests, and upon success, disribute the APK
    NOTE: if the testers email fail to receive an invitation email (the initial emai) from firebase app-distribution, please consider following this invitation link to sign-up :
    https://appdistribution.firebase.dev/i/d91e59b49b2eb22f

What could have been added, in a wider time-frame/production app :
- Fully implementing Clean Architecture (added UseCase classes)
- Better UX/UI practices
- Better SOLID Principles
- Better UniTests practices and wider implementations
- A Better and more efficient Caching system for using ROOM databse
- More tested and bug free product.

Thank you :)
