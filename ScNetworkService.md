# ScNetworkService
This class can be used for retrieve the connectivity manager and for maintain checked the connectivity status.
This class inherit from the [ScChecker](ScChecker.md).
<br />
Through the listener implementation you can know when the connectivity status changed.

> **PERMISSIONS**
> This classes need of network user permission.
> Please remember to add the follow line on your manifest:
>
> <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

#### Methods
- **ConnectivityManager getConnectivityManager()**<br />
Return the connectivity manager when available.
- **boolean isWifiEnabled()**<br />
<code>true</code> if the wifi connection is enabled.
- **boolean isMobileEnabled()**<br />
<code>true</code> if the mobile connection is enabled.
- **boolean check()**<br />
<code>true</code> if at least one connection is enabled.

### Example
For an example please take a look the demo section in the project structure.

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
