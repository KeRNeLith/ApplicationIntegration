# Hello World

## GET
curl "localhost:8080/tp1/ws/hello"


# Todo List

## GET
curl "localhost:8080/tp1/ws/todo"

## PUT
curl -X PUT -d "done=true" "localhost:8080/tp1/ws/todo/1"

## POST
curl -X POST -d "text=Ma Nouvelle 'Tâche'" "localhost:8080/tp1/ws/todo"

## DELETE
curl -X DELETE "localhost:8080/tp1/ws/todo/1"
