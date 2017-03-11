# [Firebase Android Codelab](https://codelabs.developers.google.com/codelabs/firebase-android/#0)

> For more information, visit [firebase.google.com](https://firebase.google.com/).

## Original Firebase Github Repository

[https://github.com/firebase/friendlychat](https://github.com/firebase/friendlychat)

## Create Firebase console Project

1. Go to the [Firebase console](https://console.firebase.google.com/).
2. Create a New Project.
3. Connect your Android App.
4. Add google-services.json file to your app.
5. Add google-services plugin to your app.
6. Sync your project with gradle files.

### Authentication

** Enable Authentication - [Firebase console](https://console.firebase.google.com/)**

- Enable Google as a sign-in provider from the `Firebase console > Authentication`.
- Set rules on the database from the `Firebase console > Database > Rules` like: 
``` 
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```
