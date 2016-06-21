# ScObserver
A custom observer attachable to a generic class.<br />
This classes hold all the value generated from the (public and protected) methods of the source class.
Note tha NOT all methods can be taken but will be filtered the method that return no value (`void`) and method that needed of parameters.
This class is better to use only in generic situation.
For have a complete control of the class changes I can suggest to look into Observer and Observable classes of Android (Java).

#### Methods

- **boolean isChanged()**<br />
Check if have some changes and update the current status.

- **void exclude(String... methodsName)**<br />
Exclude the methods passed from checking


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
