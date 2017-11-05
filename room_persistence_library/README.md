# [Room Persistence codelab](https://codelabs.developers.google.com/codelabs/android-persistence/#0)

> For more information, visit [developers.android.com](https://developer.android.com/topic/libraries/architecture/room.html).

## Overview

The new [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html) guarantee SQL statements at compile-time and removes boilerplate code related to storing data locally in SQLite using the following features:

- At compile time, Room validates each query against the schema, so that broken SQL queries result in compile time errors, instead of runtime failures.
- Room abstracts away some of the underlying implementation details related to working with raw SQL.
- You can use Room to observe changes to data stored in a database, and expose these changes as [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html) objects.
- Room also defines thread constraints which you can use to help address common issues that can negatively impact the performance of apps, such as accessing persistent storage from the main thread.

## Original Room Persistence Codelab Github Repository

[https://github.com/googlecodelabs/android-persistence](https://github.com/googlecodelabs/android-persistence)