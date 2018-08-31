#!/usr/bin/jjs- -fv

var uri = "http://localhost:8080/timeRegistration/api/timeentries";
var command = "curl ${uri}";
print(command);
$EXEC(command);
var result = $OUT;

print(result);
var resultAsArray = JSON.parse(result);
print(resultAsArray);

for (timeEntry in resultAsArray) {
    print(resultAsArray[timeEntry].caption + " - " + resultAsArray[timeEntry].description);
}