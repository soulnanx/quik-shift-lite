# quik-shift-lite

## Setup
The build.gradle file needs some changes, the following lines should be filled with the respective rails server addresses.

```
buildConfigField "String", "WEBSOCKET_URL", "\"ws://192.168.1.16:3000/cable\""
buildConfigField "String", "SERVER_URL"   , "\"http://192.168.1.16:3000/\""
```

## Technologies

**Android version**

* target-sdk = 27

**API**

* retrofit2
* actioncable-client-java
