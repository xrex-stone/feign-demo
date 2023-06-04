# Introduction

This repo is to demo how Spring Cloud OpenFeign works between client and server side.

# Environment

There are two application : 
- ./server : provide basic authentication API. default port is 8000
- ./client : client side to access server API.

Both are SpringBoot application, please launch server side by its SpringBoot application class.
as for client side, we can just execute unit test to observe how it works with server side.

# Server Side API
```shell
# health check
curl --location 'http://127.0.0.1:8000/health'

# call API with authentication token
curl --location 'http://127.0.0.1:8000/check_auth' \
--header 'token: 123456'

# call long query API
curl --location 'http://127.0.0.1:8000/long_query' \
--header 'token: 123456'

# create a todo item API
curl --location 'http://127.0.0.1:8000/todo' \
--header 'token: 123456' \
--header 'Content-Type: application/json' \
--data '{
    "name" : "schedule1",
    "description" : "see a doctor"
}'

# query todo items API
curl --location 'http://127.0.0.1:8000/todo' \
--header 'token: 123456'
```