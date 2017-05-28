# rsocket-requestreply-example
Example showing how to send request/reply messages between applications using [RSocket](https://github.com/rsocket).

The example starts a client and server.  The client sends 100 ping messages to the server and the server responds with a 
matching pong message.

## Prerequisites
This example requires that you have the latest version of [rsocket-java](https://github.com/rsocket/rsocket-java) installed locally.

## Running the Example
The example can be run using the following gradle command:

```
$ ./gradlew run
```

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/gregwhitaker/rsocket-requestreply-example/issues).

## License
Copyright 2017 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.