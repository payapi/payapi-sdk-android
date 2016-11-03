# PayApi SDK for Android

Android implementation of [PayApi Secure Form](https://payapi.io/apidoc/#api-Payments-PostSecureForm).

## Contents

Includes an easy-to-use PayApi SDK for any Android application, working with Swift.
In order to use the SDK, please register for a free [PayApi user account](https://input.payapi.io)

## Installation

1 - Add the repository url to your project level build.gradle
```gradle
allprojects {
    repositories {        
        maven {
            url "https://raw.githubusercontent.com/payapi/payapi-sdk-android/releases"
        }
    }
}
```
2 - Add the library as a dependency in your app module level build.gradle
```gradle
dependencies {
    ...
    compile 'io.payapi.payapisdk:payapi-sdk-android:1.0'
}
```

## Usage

```java
import io.payapi.payapisdk.SecureForm;
```

### Initialization
Initialize SDK with your credentials (Public Id and PayApi key)
```java
SecureForm secureForm =  new SecureForm(activity, "SHOP_PUBLIC_ID", "PROVIDED_API_KEY");
```

### Building a product data dictionary

```java
String message = "{" +
                "\"order\": {" +
                    "\"sumInCentsIncVat\": 122," +
                    "\"sumInCentsExcVat\": 100," +
                    "\"vatInCents\": 22," +
                    "\"currency\": \"EUR\"," +
                    "\"referenceId\": \"ref123\"" +
                "}," +
                "\"products\": [" +
                    "{" +
                        "\"quantity\": 1," +
                        "\"title\": \"Black bling cap\"," +
                        "\"priceInCentsIncVat\": 122," +
                        "\"priceInCentsExcVat\": 100," +
                        "\"vatInCents\": 22," +
                        "\"vatPercentage\": 22" +
                    "}" +
                "]," +
                "\"consumer\": {" +
                    "\"email\": \"support@payapi.io\"" +
                "}" +
        "}";
```

### Adding functionality to a button

Method to open the Secure Form on any UIButton TouchUpInside event

```java
secureForm.addSecureFormToButton(myButton, productData);
```

## Questions?

Please contact support@payapi.io for any questions.
