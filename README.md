# BirdQuestApp.

Welcome to BirdQuest, your go-to birdwatching application! BirdQuest allows you to explore and discover various bird species within a specific area. The app not only provides a platform for viewing birds but also offers navigation assistance to help you locate and observe your favorite feathered friends. Additionally, BirdQuest includes innovative features such as bird species tips and information, as well as user authentication through Firebase.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Firebase Setup](#firebase-setup)
  - [Dependancies](#dependancies)
  - [Features](#features)
- [Contributing](#contributing) 
- [License](#license)

## Getting Started

To get started with BirdQuest, follow the instructions below.

### Prerequisites

Make sure you have the following software and tools installed before running the application:

-[Android Studio](https://developer.android.com/studio): The official integrated development environment (IDE) for Android app development.

-[Kotlin](https://kotlinlang.org/): The programming language used for developing the Android app.

## Installation

1.Clone the repository:
 ```bash
    git clone https://github.com/ST10089495/BirdQuestApp..git
 ```
2.Open the project in Android Studio.

3.Build and run the app on an emulator or a physical device.

## Firebase Setup

BirdQuest uses Firebase for user authentication and data storage. Follow these steps to set up Firebase for the project:

1.Create a Firebase project at [Firebase Console](https://console.firebase.google.com/).

2.Add an Android app to your Firebase project.

3.Follow the setup instructions to add the google-services.json file to your Android project.

4.Enable Firebase Authentication and Firestore in your Firebase project.

5.Update the Firebase configuration in the google-services.json file with your project details.

## Dependancies

BirdQuest relies on the following libraries and tools:

[Firebase Authentication](https://firebase.google.com/docs/auth): For user authentication.
[Firebase Firestore](https://firebase.google.com/docs/firestore): For storing bird observations.
[Glide](https://github.com/bumptech/glide): For efficient image loading.
[Google Maps API](https://developers.google.com/maps/documentation/android-sdk/overview): For displaying maps and navigation.

To add these dependencies to your project, include the following lines in your build.gradle files:

```gradle
implementation 'com.google.firebase:firebase-auth:22.0.0'
implementation 'com.google.firebase:firebase-firestore:24.0.0'
implementation 'com.github.bumptech.glide:glide:4.12.0'
implementation 'com.google.android.gms:play-services-maps:17.0.1'
```
Make sure to check for the latest versions of these dependencies on their respective documentation pages.

## Features

-Bird Species Tips: Get valuable tips and information on different bird species in South Africa.

-User Authentication: Register and log in to the app to save and access your bird observations across devices.

-Navigation Assistance: Navigate towards specific birds for a better birdwatching experience.

## Contributing

If you'd like to contribute to BirdQuest, feel free to fork the repository and submit pull requests. We welcome any improvements, bug fixes, or additional features.

## License

[MIT License](https://opensource.org/licenses/MIT).

Happy birdwatching with BirdQuest! 🦜🔍

