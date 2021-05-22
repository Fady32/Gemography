# Solution About

Developing a REST microservice that list the languages used by the 100 trending public repos on GitHub.
For every language,and then calculate the attributes below:
- Number of repos using this language
- The list of repos using the language

The response data sorted from the max to the lower Language Repos

# Used Technologies:
- Spring Boot Framework
- Java 11
- REST API
- Swagger API Documentation

# Run: 

- use command : mvn spring-boot:run 
- open browser with url http:localhost:8080/ 
  it will redirect to swagger page that contains the API documentation.
  
# Caching data:

- In case Git response with limited number of retries No Problem , 
  The API will respond with the last updated Languages from caching     
  
# Response example: 

- [
    {
-      "JavaScript": {
 -     "ReposCount": 5,
 -     "ReposURls": [
        {
          "id": 367450130,
          "node_id": "MDEwOlJlcG9zaXRvcnkzNjc0NTAxMzA=",
          "name": "semana-javascript-expert04",
          "full_name": "ErickWendel/semana-javascript-expert04",
          "private": false,
          "owner": {
          "login": "ErickWendel",
          "id": 8060102,
          "node_id": "MDQ6VXNlcjgwNjAxMDI=",
          "avatar_url": "https://avatars.githubusercontent.com/u/8060102?v=4",
          "gravatar_id": "",
          "url": "https://api.github.com/users/ErickWendel",
          "html_url": "https://github.com/ErickWendel", ......
