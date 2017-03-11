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

- Enable Google as a sign-in provider from the _`Firebase console > Authentication`_.
- Set rules on the database from the _`Firebase console > Database > Rules`_ like: 
``` 
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```

### Messages

** Import Messages **

- Go to the _`Firebase console > Database`_.
- Select _`Import JSON`_ from the overflow menu of the Database.
- Import the _`initial_messages.json`_ file.

** Test message sync **

- From the _`Firebase console > Database > DATA`_ tab, select the _`+`_ sign on the _`messages`_ element.
- Give the new element a name of -ABCD (note the '**`-`**' sign).
- Select the _`+`_ sign on the -ABCD element.
- Give the new element a name of _`name`_ and value of `your name`.
- Select the _`+`_ sign on the -ABCD element.
- Give the new element a name of _`text`_ and value of `Hello, World!`.
- Select Add. 