# react-native-external-storage-permission

react native package to prompt user to allow access to manage all files
## Introduction
This Package (react-native-external-storage-permission) is implemented in java and it is registered in react native as a native module. The package solves the issues about implementing **MANAGE_EXTERNAL_STORAGE** Permission that is need to access all files on android phone in React Native.

The package is developed as a result of changes in Google Play Store Privacy Policy and the need to implement **MANAGE_EXTERNAL_STORAGE** Permission in React Native. Google introduced this Permission from Android 11 (API level 30) or higher and its the ONLY way to access and Manage All files.

This is how this package implements the permission. It implements by prompting the user to allow the app to access and manage all files.
![Permission Access](./src/img/access.jpg?raw=true "Title")

## Installation

```sh
npm install react-native-external-storage-permission
```
## Android Setup
### Go to ---
*<---Project directory---/>/android/app/src/main/AndroidManifest.xml*
then add the following permissions
```
    <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
```

## Usage

```js
import  ManageExternalStorage  from 'react-native-external-storage-permission';

// ...

    const result = await ManageExternalStorage.checkAndGrantPermission();
    if (result) {
        // success
    } else {
        // fail
    }

// ...

```


## License

MIT

---

## Author
 ### Name  :  **Leon Redfish**
 
 ### Email  :   **leonredfish@gmail.com || leon_redfish@163.com**
 ### Website :  no websites yet
