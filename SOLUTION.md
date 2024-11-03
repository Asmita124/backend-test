# MY SOLUTION

## Start locally

```shell
./gradlew clean build && docker compose up --build
```
Swagger is on <http://localhost:8080/swagger-ui.html>.

## Initialize the Rover
Before executing any move commands, you must initialize the rover. 
Use the '/rover/initialize' endpoint with a JSON body containing the starting position and direction.
Here’s an example of how to provide the initialization command:

```shell
{
  "x": 0,
  "y": 0,
  "direction": "N"
}

```


If facing North (y increases), moving forward would increase the y-coordinate.
If facing South (y decreases), moving forward would decrease the y-coordinate.
If facing East (x increases), moving forward would increase the x-coordinate.
If facing West (x decreases), moving forward would decrease the x-coordinate.
TODO


bonus point
build pipeline and metrics and coverage

see the need for kafka or a database


solution file 

