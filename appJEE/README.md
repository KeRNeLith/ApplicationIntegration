# ApplicationIntegration
Work on Application integration on Java platform (JavaEE)


# Sample Java EE application

## Hello World

curl http:/location/ws/hello

## Todo List

**List Todos**

curl http:/location/ws/todo

**Add Todo**

curl -X POST -d text="My Todo" http://location/ws/todo

**Delete Todo**

curl -X DELETE http://location/ws/todo/{id}

**Check Todo**

curl -X PUT -d done="true" http://location/ws/todo/{id}

**Uncheck Todo**

curl -X PUT -d done="false" http://location/ws/todo/{id}
