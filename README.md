[![Build Status](https://travis-ci.org/eastbanctechru/Reamp.svg?branch=master)](https://travis-ci.org/eastbanctechru/Reamp)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/MIT)

# Reamp

An easy, powerful, and flexible MVP/MVVM library for Android apps
 
# What you get by using Reamp

 - Easy way to handle async operations
 - Easy way to saving and restoring UI state
 - UI errors handling
 - Ability to test your UI login with regular JUnit tests
 
 - No code generation
 - No reflection
 - No additional plugins needed
 - Kotlin ready
 
# Reamp in a few words

Every ReampView (activity, fragment, whatever) has a presenter and a view state.

The presenter lives all the time across view's life cycle and dies only when the view explicitly closes (for instance, by pressing the back button).

The view state contains all the data your view needs.

Update the view state and send a notification to the view when you need to update the screen without having to pay attention to the life cycle.

Reamp will do all the remaining work: check view availability, save and restore the state if needed, and more.

# Dependency

```groovy
// root build.gradle file

allprojects {
    repositories {
        jcenter()
    }
}
```

```groovy
// module build.gradle file
dependencies {
  compile 'etr.android.reamp:reamp:1.0.2'
}
```

## License
```
The MIT License (MIT)

Copyright (c) EastBanc Technologies Russia

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
