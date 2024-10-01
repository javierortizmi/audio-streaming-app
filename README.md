# Music Player App

<a href="https://appetize.io/app/2va72f4rcuub446rz5mn22ve6m?audio=true&device=pixel7pro&osVersion=13.0" target="_blank"><img src="./media/preview.gif" width="500" alt="App Demo"></a>

## Overview

Project made in collaboration with [August Pratt](https://github.com/jpratt21). We wanted to create a music player app that controls the volume of the device with the speed of the user.

This is an Android Studio Project developed in Kotlin, using a wide range of technologies such as Google Firebase, GPS location or Google Ads.

[![DEMO](https://img.shields.io/badge/TRY%20THE-DEMO-blue?style=for-the-badge&logoColor=white&labelColor=%23545454&color=%238983e6)](https://appetize.io/app/2va72f4rcuub446rz5mn22ve6m?audio=true&device=pixel7pro&osVersion=13.0)

## Technologies Used

[![Android_Studio](https://img.shields.io/badge/ANDROID_STUDIO-%234fae53?style=for-the-badge&logo=androidstudio&logoColor=white&labelColor=black)](https://developer.android.com/)
[![Kotlin](https://img.shields.io/badge/KOTLIN-%237f52ff?style=for-the-badge&logo=kotlin&logoColor=white&labelColor=black)](https://kotlinlang.org/)
[![Firebase](https://img.shields.io/badge/FIREBASE-%23FFCA28?style=for-the-badge&logo=firebase&logoColor=white&labelColor=black&color=%23FFCA28)](https://firebase.google.com/)
![GPS](https://img.shields.io/badge/GPS-%4285F4?style=for-the-badge&logo=googlemaps&logoColor=white&labelColor=black)
[![Google_Ads](https://img.shields.io/badge/Google%20ads-%234285F4?style=for-the-badge&logo=googleads&logoColor=white&labelColor=black)](https://ads.google.com/)

## Requirements

For this project you will need a Google Ads ID credential which you can create and use [here](https://apps.admob.com/).

![Google Ads ID](media/Google_Ads_ID.png)

Furthermore, you will have to create and use your own Firebase database, importing your google-services.json or linking your project to Firebase in Android Studio. You will find more info [here](https://firebase.google.com/docs/android/setup).

![Connect to Firebase](media/Connect_Firebase.png)

## Project

This is the general structure of the project.

- Inside `./app/src/main/java/com/example/music_player/`

  - **MainActivity.kt**: Activity for the home page. Links the views from the xml with the functionalities of the app.
  - **MusicActivity.kt**: Activity for the current song page. Links the views from the xml with the functionalities of the app.
  - **SettingsActivity.kt**: Activity for the settings page. Links the views from the xml with the functionalities of the app.
  - **GPSSpeed.kt**: Model for the speed tracking using the GPS from the device.
  - **PlayerManager.kt**: Manager for the ExoPlayer. Useful for using the same media3 player in the whole app.
  - **Song.kt**: Model for each song.
  - **Songs.kt**: Model for a group of songs (ArrayList).
  - **SongAdapter.kt**: Adapter for the recycler view in the home page. Retrieves all the songs and adds the necessary functionality.

- Inside `./app/src/main/res/`

  - **drawable**: folder in which all the images/icons are stored.
  - **layout**: all the xml files necessary for the views of the app.
  - **transition**: all the xml files used for the transitions between layouts.
  - **values**: inside, there are folders for the strings, colors, and themes used in the app.

- Inside `./app/src/main/`

  - **AndroidManifest.xml**: Stores the necessary permissions for the app to run. Also, all the activities are declared in this file.

## Installation

1. `Clone` this repository into your Android Studio projects folder.

   ```bash
   git clone https://github.com/javierortizmi/MusicPlayerApp.git
   ```

2. Open the MusicPlayerApp with Android Studio.

3. Add your own Firebase database (`google-services.json`) and credentials for Google Ads (in `AndroidManifest.xml`).

4. Run the project in an emulator or an actual Android physical device.

## License

[![License](https://img.shields.io/badge/LICENSE-MIT-%23FFCA28?style=for-the-badge&logoColor=white&labelColor=black&color=%23808080)](LICENSE)
