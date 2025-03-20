# TPGDemo Android

This app interacts with The Cat API, enabling users to perform CRUD operations (Create, Read, Update, Delete) on cat images.

Features:
- Fetch Random Images – Users can browse random cat images from The Cat API.
- Upload Images – Users can upload their own cat images to their account, linked with an API key.
- Delete Images – Users can remove uploaded images from their account.
- Update Uploads List – Users can refresh and manage their uploaded images.

the app has two sections uploads section and public gallery section for the user to navigate.

## Clone the Repository & Setup API Key

### Clone the Repository

```bash
git clone <repo-link>
cd tpgdemo
```

### Add API Key
1. Get your api key from https://thecatapi.com/
2. Open the project in Android Studio.
3. Locate the local.properties file (or create it if missing).
4. Add your API key:

```bash
API_KEY=your_api_key_here
```

## Build & Execution Instructions

### Prerequisites

- Android Studio (Latest version)
- JDK 11 or higher
- Android SDK

### Steps

1. Open Android Studio.
2. Click on Open an Existing Project.
3. Select the cloned repository folder.
4. Sync Gradle and Build the Project.

### Run the App

1. Connect an Android device via USB or use an emulator.
2. Click Run in Android Studio.

## Design Decisions

### MVVM Architecture

1. Separation of Concerns

- Divides app into Model (data), View (UI), and ViewModel (logic) for better organization.
- Reduces code complexity, making updates and debugging easier.

2. Testable

- Uses StateFlow for automatic UI updates, reducing manual work.
- ViewModel is independent of UI, making unit testing easier.

### Repository Pattern

Instead of ViewModel directly making the api calls , the repository provides the data.

This approach helps make the code retrieving the data loosely coupled from ViewModel

### Dependency Injection

- Makes the ViewModel Testable by passing fake network service

- Code is loosely coupled, so refactoring one section of code does not impact another section of code

### API Interface

- Uses Retrofit for API communication.
- Handles API responses with coroutines for asynchronous operations.

### Tests

- Fake Repository – Simulates API responses for controlled testing.
- Unit Tests – Validates business logic in ViewModel using a Fake Repository.

### Improvements that can be done

- Improve efficiency by loading images in batches instead of all at once and do pagination
- Cache fetched images.
- Allow users to upload images from the device gallery.
- Upload large images using Woker manager.
