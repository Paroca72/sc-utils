# ScChecker
This is very simple class utility that periodically repeat a check method.
This is an abstract class and cannot be instanced directly.
<br />
Through the listener implementation you can know when the status of check changed.
This can be very useful in some case. 
For example when you must check the internet connection status or all kind of checks when the user must be advised only when some changed happen.

#### Getter and Setter
- **get/setStartDelay()**  -> long value, default: <code>100 milliseconds</code><br />
When start to check the delay in milliseconds
- **get/getCheckRate()**  -> long value, default: <code>1000 milliseconds</code><br />
The waiting millisecond between a check and the next
- **get/getMode()**  -> Modes value, default: <code>FIXED_DELAY</code><br />
The waiting mode between a check and the next.<br />
Modes.FIXED_DELAY: start to wait once check is finished<br />
Modes.FIXED_RATE: check at fixed rate time

#### Methods
- **boolean check()**<br />
Abstract method must be overridden. This method will be called periodically in according with the specified settings.
- **void start()**<br />
Start to check.
- **void stop()**<br />
- **void stop(boolean force)**<br />
Stop to check. If <code>force</code> the check will be interrupted even if not finished.
- **void setCheckerListener(CheckerListener listener)**<br />
Attach the listener.

### Listener
```java
    public interface CheckerListener {

        void onSuccess();

        void onFail();

        void onChangeState(boolean result);
        
    }
```

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
