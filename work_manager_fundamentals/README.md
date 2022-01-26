# [WorkManager Fundamentals codelab](https://developer.android.com/codelabs/kotlin-android-training-work-manager)

> For more information, visit [developers.android.com](https://developer.android.com/topic/libraries/architecture/workmanager).

##Introduction

DevByteWorkManager app displays a list of DevByte videos. DevByte videos are
short videos made by the Google Android developer relations team to introduce
new developer features on Android. This app fetches the DevByte video playlist
from the network using the Retrofit library and displays it on the screen. The
network fetch is scheduled periodically once a day using the WorkManager.
Constraints like device idle, unmettered network and so on are added to the work
request to optimise the battery performance.

## Original WorkManager Fundamentals Github Repository

[https://github.com/google-developer-training/android-kotlin-fundamentals-apps](https://github.com/google-developer-training/android-kotlin-fundamentals-apps/tree/master/DevBytesWorkManager)