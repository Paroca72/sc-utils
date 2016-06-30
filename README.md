# ScUtils
This is a library of utilities classes

- **[ScChecker](ScChecker.md)**<br />
This is very simple class that periodically repeat a check method.

- **[ScLocationService](ScLocationService.md)**<br />
Retrieve the location manager and for maintain checked the location accessibility.

- **[ScNetworkService](ScNetworkService.md)**<br />
Retrieve the connectivity manager and for maintain checked the connectivity status.

- **[ScObserver](ScObserver.md)**<br />
A custom observer attachable to a generic class.

- **[ScPathMeasure](ScPathMeasure.md)**<br />
Extend the PathMeasure because the original class not consider the contours in its totality.


# Usage

via Gradle:
<br />
Add it in your root build.gradle at the end of repositories:
```java
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Add the dependency
```java
dependencies {
    ...
    compile 'com.github.paroca72:sc-utils:1.2.1'
}
```

#License
<pre>
 Copyright 2015 Samuele Carassai

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in  writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,  either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
</pre>
