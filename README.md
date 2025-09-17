# VehicleCompanion

This README provides an overview of the project's structure, how to run it, and the key architectural decisions made during development.

-----

## Running

To run the project, you'll need **Android Studio Giraffe | 2022.3.1** or newer with the **Android Gradle Plugin (AGP) version 8.1.1**.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/VehicleCompanion.git
    cd VehicleCompanion
    ```
2.  **Open in Android Studio:**
    Open the cloned project in Android Studio. The IDE will automatically download the necessary Gradle dependencies.
3.  **Run the app:**
    Select a device or emulator and click the "Run 'app'" button in the toolbar. The app will build and install on your selected device.

-----

## Architecture & Design Choices

The application's architecture is built on the principles of **Modularity**, **Unidirectional Data Flow**, and **Scalability** to ensure a robust and maintainable codebase.

### 1\. MVVM Architecture

The project follows the **Model-View-ViewModel (MVVM)** architectural pattern.

  * **View (UI Layer):** Composable functions in Jetpack Compose and Fragments display the UI and observe data from the ViewModel.
  * **ViewModel (State Layer):** Holds the UI state and business logic. It exposes data streams (e.g., using `StateFlow`) to the UI and handles user events. It has no direct reference to the View.
  * **Model (Data Layer):** The repository and data sources. The repository acts as a single source of truth, abstracting data from different sources like a local database or network API.

### 2\. Dependency Injection with Hilt

**Hilt**, a dependency injection library for Android, is used to manage and provide dependencies throughout the application. This decouples classes, making the code more modular and easier to test. It simplifies the setup by automatically generating the necessary Dagger components.

### 3\. Data Persistence with Room

The app uses **Room** for local data persistence. Room is a database object mapping library that provides an abstraction layer over SQLite, making database interactions simpler and more robust. It's used to store vehicle details and favorite places.

### 4\. Asynchronous Operations with Kotlin Coroutines

**Kotlin Coroutines** are used for all asynchronous and non-blocking operations, such as network requests and database queries. This ensures the main UI thread remains responsive, preventing ANR (Application Not Responding) errors.

### 5\. Network Communication with Retrofit & OkHttp

**Retrofit** and its underlying HTTP client, **OkHttp**, are used for network requests.

-----

## How to Run Tests

The project includes unit and instrumentation tests to ensure code quality and functionality.

1.  **Unit Tests:**
    Run unit tests on the JVM to test the business logic in the ViewModel and repository layers.
      * In Android Studio, right-click on the `app/src/test/java` directory and select **Run 'Tests in 'test''**.
      * Alternatively, run from the command line:
        ```bash
        ./gradlew testDebugUnitTest
        ```
2.  **Instrumentation Tests:**
    Run instrumentation tests on an Android device or emulator to test UI components and database interactions.
      * In Android Studio, right-click on the `app/src/androidTest/java` directory and select **Run 'Tests in 'androidTest''**.
      * Alternatively, run from the command line:
        ```bash
        ./gradlew connectedCheck
        ```

-----

## Error/Empty State Handling

The application handles various error and empty states to provide a good user experience.

  * **Empty State:** When a list (e.g., vehicles, places, favorites) is empty, the UI displays a clear message to guide the user on how to add new data.
  * **Error State:** In case of data loading or processing errors, an error message is shown to the user. This is particularly important for network-based operations. The architecture's state management ensures the UI can react to these states gracefully, for example, by showing a "Failed to load data".

-----

## What I’d Build Next

If I had more time, I would focus on the following features and improvements:

  * **Cloud Sync:** Implement a backend service and integrate it with the app to allow users to sync their vehicle data across multiple devices. This would use an API layer in the data source.
  * **Maintenance Reminders:** Add a notification system to remind users of upcoming maintenance tasks based on mileage or time intervals. This would involve a background service and WorkManager.
  * **Advanced Statistics:** Generate more detailed reports and visualizations, such as fuel efficiency trends over time, total cost of ownership, and maintenance spending breakdowns.
