# StarWars API test application

## Requirements
1. Provide API endpoint `/information`, which will integrate with external [api swapi.dev](https://swapi.dev) and provide 
response in format
    ```json
    {
        "starship": {
            "name": "TIE Advanced x1",
            "model": "Twin Ion Engine Advanced x1",
            "class": "Starfighter"
        },
        "crew": 342953,
        "leiaOnThePlanet": true
    }
    ```
   **Consideration**: in original requirement crew and leiaOnThePlanet is String,
   but I made a choice to make them typed. In real project this needs to be confirmed with PM

   **Consideration**: I also decided to add endpoint `api/v1/information`, which is giving extra flexibility by
   providing versioning
2. `starship` field should indicate, starship used by Darth Vader and should return 
   empty object `{}`, if no starship is found

   **Consideration**: `starships` field of `People` entity is list, so it can contain more than one element,
   but our contract allows one or zero information, so in case, if list consist of 2 elements
   we can return first/random element or error, I decided to throw validation error.
   In real scenario we should work with PM to resolve this.
3. `crew` field should return number of crew on `Death Star` starship, or 0 if there's no crew on board.

   **Consideration**: I expect starship to be present and return validation error, if it's not found
4. `leiaOnThePlanet` field should have value true, if `Leia Organa` is on the `Alderaan`, false otherwise
   
   **Consideration**: I expect both `Leia Organa` and `Alderaan` to be present and return validation error otherwise 

## Running

Application require Java 17

### Start web server
```bash
./gradlew clean bootRun
```
API can be queried with curl:
```bash
curl http://127.0.0.1:8080/information
```
Swagger UI is available: http://localhost:8080/swagger-ui/index.html

### Tests

Generate code coverage report:
```bash
./gradlew clean jacocoTestReport
```
Report will be available at local path `build/reports/jacoco/test/html/index.html`

Running integration test with real API:
```bash
./gradlew clean build -x test integrationTest
```