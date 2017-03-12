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

1. Go to the _`Firebase console > Database`_.
2. Select _`Import JSON`_ from the overflow menu of the Database.
3. Import the _`initial_messages.json`_ file.

** Test message sync **

1. From the _`Firebase console > Database > DATA`_ tab, select the _`+`_ sign on the _`messages`_ element.
2. Give the new element a name of -ABCD (note the '**`-`**' sign).
3. Select the _`+`_ sign on the -ABCD element.
4. Give the new element a name of _`name`_ and value of `your name`.
5. Select the _`+`_ sign on the -ABCD element.
6. Give the new element a name of _`text`_ and value of `Hello, World!`.
7. Select Add. 

### On-device App Indexing

** Test On-device App Indexing **

1. Start up the `FriendlyChat app` and send a new message with the text Hi world!
2. Go to the `Google app`, switch to In Apps and search for Hi world.
3. Confirm that the message can be found in the Google app.
4. Tap on the result and confirm the FriendlyChat app opens.

### Notifications

** Test Background Notifications **

1. `Run` the updated application.
2. Hit the device's home button (or otherwise send the app to the `background`).
4. In `Firebase console` select `Notifications` from the left navigation bar.
5. Set `Message` Text to "Friendly Chat?".
6. Select the app we connected earlier as the `App target`.
7. Click `Send Message`
8. Confirm that message is received and notification is displayed on the device. The user should receive a notification that takes them back to the application when tapped.

### Remote Config

** Test Remote Config **

1. Go to `Firebase console > Remote Config`.
2. Click on `Add Parameter`.
3. Set the `Parameter key` to _`friendly_msg_length`_ and the `Parameter value` to `20`.
4. Click `Publish Changes` when you're done.
