# MockStoreCMP

A Compose Multiplatform application that fetches product data from a mock backend (FakeStoreAPI) and
presents it on a scrollable, filterable UI. The app demonstrates clean architecture principles with
shared business logic, dependency injection, navigation and platform‐specific configuration,
allowing Android and iOS targets to reuse as much code as possible.

---

## Overview

A Compose Multiplatform application that fetches product data from a mock backend (FakeStoreAPI) and
presents it on a scrollable, filterable UI. The app demonstrates clean architecture principles with
shared business logic, dependency injection, navigation and platform‐specific configuration,
allowing Android and iOS targets to reuse as much code as possible.

## Features

1. **Product Listing**
    - Fetches a list of products from [fakestoreapi](https://fakestoreapi.com/products)
    - Displays product image, title, price, category, and rating
    - Filter products by category using Chips
2. **Product Details**
    - Tapping a product navigates to a details screen that shows a larger image, full description,
      price, category, and rating
    - Back navigation to return to the listing
3. **Category‐Based Loading**
    - Contains available categories based on `products/categories`
    - Loads products for a selected category via `products/category/{category}`
4. **Error & Loading States**
    - Shows a centered spinner while loading data
    - Displays a retry button and an error message if the network or parsing fails

## Architecture

The application places most of the code in the `commonMain` shared module to maximize reuse between
Android and iOS. Key packages in commonMain include:

- **data/**
  Contains concrete implementations of `HttpClient`, shared data transfer objects, and any future
  persistence logic.

- **domain/**
  Defines data models and platform-agnostic app logic that can be used by all features.

- **di/**  
  Declares shared di modules as well as the logic for initializing Koin.

- **presentation/**  
  Contains the navigation logic and any part of UI that is used in more than one feature.


- **feature/**  
  Each feature could be a self-contained module with its own sub-modules:
    - **data/**  
      Contains data transfer objects and feature-specific data implementations, such as API clients.
      These classes implement the interfaces defined in the feature’s domain layer.
    - **domain/**  
      Defines interactor classes and feature-specific interfaces and domain models. All interfaces
      live here.
    - **presentation/**  
      Contains the feature’s ViewModel and composables. ViewModels depend only on domain interfaces.
    - **di/**  
      Declares Koin modules that bind the feature’s interfaces to their concrete implementations in
      data/, and provide ViewModel instances.

## Libraries Used

- [**Koin**](https://github.com/InsertKoinIO/koin)  
  Lightweight dependency injection framework.

- [**Ktor**](https://ktor.io/)  
  Asynchronous HTTP client, used with the OkHttp Engine on Android and Darwin Engine on iOS.

- [**Coil**](https://github.com/coil-kt/coil)  
  Image loading library.

- [**Common ViewModel
  **](https://central.sonatype.com/artifact/org.jetbrains.androidx.lifecycle/lifecycle-viewmodel)  
  Provides multiplatform ViewModel support while maintaining lifecycle awareness on
  Android.

- [**JetBrains' Navigation
  **](https://central.sonatype.com/artifact/org.jetbrains.androidx.navigation/navigation-compose)  
  Simplifies navigation between composables.

## Future Improvements

- **Paging**
    - If FakeStoreAPI supported pagination parameters, integrate pager implementation to improve
      performance with large datasets.

- **Caching**
    - Implement local persistence using tools like SQLDelight or Realm to support offline access and
      reduce unnecessary network requests.

- **Dark Mode & Theming**
    - Support dark mode and custom theming using Material3 dynamic color theming or manually defined
      light/dark themes.

## Running the Project

To run the project locally, follow these steps:

- **Clone the Repository**
   ```bash
   git clone https://github.com/beHqo/MockStoreCMP.git
   cd MockStoreCMP

- **Open in Android Studio**

    - Open Android Studio.
    - Select File > Open... and choose the root directory of the cloned project.
    - Let Gradle sync and finish indexing the project.

- **Run the App**

    - Choose your desired run configuration.
    - Click the green Run ▶️ button in the toolbar.
    - The app should build and launch, displaying the product list.